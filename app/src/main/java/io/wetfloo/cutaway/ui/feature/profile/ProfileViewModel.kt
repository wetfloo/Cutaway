package io.wetfloo.cutaway.ui.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.data.repository.profile.ProfileRepository
import io.wetfloo.cutaway.misc.utils.savedastate.StateSaver
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val stateSaver = StateSaver<ProfileState>(
        savedStateHandle = savedStateHandle,
        key = "PROFILE_STATE",
        defaultState = ProfileState.Idle,
    )
    private var stateValue by stateSaver
    val stateFlow = stateSaver.state

    private val _error: Channel<UiError> = Channel()
    val error = _error.receiveAsFlow()

    fun load() {
        when (val currentState = stateValue) {
            is ProfileState.Ready -> {
                stateValue = currentState.copy(
                    isUpdating = true,
                )

                viewModelScope.launch {
                    updateProfile().handleStateResult()
                }
            }

            ProfileState.Idle -> {
                stateValue = ProfileState.Loading
                viewModelScope.launch {
                    updateProfile().handleStateResult()
                }
            }

            ProfileState.Loading -> Unit
        }
    }

    private suspend fun updateProfile(): Result<ProfileState.Ready, UiError> = profileRepository
        .loadProfileInformation()
        .map(ProfileState::Ready)
        .mapError { UiError.Res(R.string.profile_failure_load) }

    private suspend fun Result<ProfileState, UiError>.handleStateResult() {
        when (this) {
            is Err -> _error.send(this.error)
            is Ok -> stateValue = this.value
        }
    }

    fun showEditingNotSupported() {
        viewModelScope.launch {
            _error.send(UiError.Res(R.string.profile_edit_not_implemented_error))
        }
    }
}
