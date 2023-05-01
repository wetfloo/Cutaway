package io.wetfloo.cutaway.ui.feature.searchuser

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.misc.utils.savedastate.StateSaver
import io.wetfloo.cutaway.ui.feature.searchuser.state.SearchUserState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
class SearchUserViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val stateSaver = StateSaver<SearchUserState>(
        savedStateHandle = savedStateHandle,
        key = "SEARCH_USER_STATE",
        defaultState = SearchUserState.Idle,
    )
    private var stateValue by stateSaver
    val stateFlow = stateSaver.state

    private val _error: Channel<UiError> = Channel()
    val error = _error.receiveAsFlow()

    var query by savedStateHandle.saveable {
        mutableStateOf("")
    }

    fun search(query: String) {
        TODO()
    }
}
