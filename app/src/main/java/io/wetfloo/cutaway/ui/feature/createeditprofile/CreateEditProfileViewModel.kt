package io.wetfloo.cutaway.ui.feature.createeditprofile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.data.repository.createeditprofile.CreateEditProfileRepository
import io.wetfloo.cutaway.misc.utils.savedastate.StateSaver
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditProfileState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class CreateEditProfileViewModel @Inject constructor(
    private val createEditProfileRepository: CreateEditProfileRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val stateSaver = StateSaver<CreateEditProfileState>(
        savedStateHandle = savedStateHandle,
        key = "CREATE_EDIT_PROFILE_STATE",
        defaultState = CreateEditProfileState.Idle,
    )
    private var stateValue by stateSaver
    val stateFlow = stateSaver.state

    private val _error: Channel<UiError> = Channel()
    val error = _error.receiveAsFlow()

    companion object {
        private const val TAG = "EditProfileViewModel"
    }
}
