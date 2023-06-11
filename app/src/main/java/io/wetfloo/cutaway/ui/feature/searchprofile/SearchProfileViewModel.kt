package io.wetfloo.cutaway.ui.feature.searchprofile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.onFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.Res
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.core.commonimpl.handleStateResult
import io.wetfloo.cutaway.data.model.searchprofile.SearchHistoryItem
import io.wetfloo.cutaway.data.repository.searchprofile.SearchProfileRepository
import io.wetfloo.cutaway.misc.utils.savedastate.StateSaver
import io.wetfloo.cutaway.ui.feature.searchprofile.state.SearchHistoryState
import io.wetfloo.cutaway.ui.feature.searchprofile.state.SearchProfileState
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class SearchProfileViewModel @Inject constructor(
    private val searchProfileRepository: SearchProfileRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val stateSaver = StateSaver<SearchProfileState>(
        savedStateHandle = savedStateHandle,
        key = "SEARCH_USER_STATE",
        defaultState = SearchProfileState.Idle,
    )
    private var stateValue by stateSaver
    val stateFlow = stateSaver.state

    val searchHistoryState: StateFlow<SearchHistoryState> = searchProfileRepository
        .searchHistory
        .map { searchHistoryItems ->
            searchHistoryItems?.let(SearchHistoryState::Ready) ?: SearchHistoryState.Loading
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchHistoryState.Idle,
        )

    private val _error: Channel<UiError> = Channel()
    val error = _error.receiveAsFlow()

    var query by savedStateHandle.saveable { mutableStateOf("") }
        private set

    private var autoSearchJob: Job? = null

    fun updateQuery(query: String) {
        this.query = query
        autoSearchJob?.cancel()
        if (query.isBlank()) {
            // clear up any search items if search field is cleared
            stateValue = SearchProfileState.Idle
            return
        }
        autoSearchJob = viewModelScope.launch {
            delay(700.milliseconds)
            search()
        }
    }

    fun search() {
        when (val currentState = stateValue) {
            SearchProfileState.Idle -> viewModelScope.launch {
                handle(
                    query = query,
                    loadingValue = SearchProfileState.Loading,
                )
            }

            SearchProfileState.Loading -> return

            is SearchProfileState.Found -> viewModelScope.launch {
                handle(
                    query = query,
                    loadingValue = currentState.copy(isLoading = true),
                )
            }
        }
    }

    fun deleteHistoryItem(item: SearchHistoryItem) {
        viewModelScope.launch {
            searchProfileRepository
                .deleteItem(item)
                .mapError { genericError }
                .onFailure { _error.send(it) }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            searchProfileRepository.clearHistory()
        }
    }

    private suspend fun searchForUser(query: String): Result<SearchProfileState.Found, UiError> =
        searchProfileRepository
            .search(query)
            .map(SearchProfileState::Found)
            .mapError { genericError }

    private suspend fun handle(
        query: String,
        loadingValue: SearchProfileState,
    ) {
        handleStateResult(
            previousValue = stateValue,
            loadingValue = loadingValue,
            valueReceiver = { stateValue = it },
            errorReceiver = { _error.send(it) },
        ) {
            searchForUser(query)
        }
    }

    private val genericError
        get() = Res(R.string.search_profile_failure_generic)
}
