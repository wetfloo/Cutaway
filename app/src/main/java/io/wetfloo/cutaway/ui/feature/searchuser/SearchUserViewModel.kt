package io.wetfloo.cutaway.ui.feature.searchuser

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.core.commonimpl.handleStateResult
import io.wetfloo.cutaway.data.repository.searchuser.SearchUserRepository
import io.wetfloo.cutaway.misc.utils.savedastate.StateSaver
import io.wetfloo.cutaway.ui.feature.searchuser.state.SearchHistoryState
import io.wetfloo.cutaway.ui.feature.searchuser.state.SearchUserState
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
class SearchUserViewModel @Inject constructor(
    private val searchUserRepository: SearchUserRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val stateSaver = StateSaver<SearchUserState>(
        savedStateHandle = savedStateHandle,
        key = "SEARCH_USER_STATE",
        defaultState = SearchUserState.Idle,
    )
    private var stateValue by stateSaver
    val stateFlow = stateSaver.state

    val searchHistoryState: StateFlow<SearchHistoryState> = searchUserRepository
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
        if (query.isBlank()) return
        autoSearchJob = viewModelScope.launch {
            delay(700.milliseconds)
            search(query)
        }
    }

    fun search(query: String) {
        when (val currentState = stateValue) {
            SearchUserState.Idle -> viewModelScope.launch {
                handle(
                    query = query,
                    loadingValue = SearchUserState.Loading,
                )
            }

            SearchUserState.Loading -> return

            is SearchUserState.Found -> viewModelScope.launch {
                handle(
                    query = query,
                    loadingValue = currentState.copy(isLoading = true),
                )
            }
        }
    }

    private suspend fun searchForUser(query: String): Result<SearchUserState.Found, UiError> =
        searchUserRepository
            .search(query)
            .map(SearchUserState::Found)
            .mapError { UiError.Res(R.string.search_user_failure_generic) }

    private suspend fun handle(
        query: String,
        loadingValue: SearchUserState,
    ) {
        handleStateResult(
            previousValue = stateValue,
            loadingValue = loadingValue,
            valueReceiver = { stateValue = it },
            errorReceiver = _error::send,
        ) {
            searchForUser(query)
        }
    }
}
