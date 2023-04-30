package io.wetfloo.cutaway.ui.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.StateViewModel
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.data.repository.profile.ProfileRepository
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileEvent
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    savedStateHandle: SavedStateHandle,
) : StateViewModel<ProfileState, ProfileEvent, UiError>(
    savedStateHandle = savedStateHandle,
    savedStateKey = STATE,
    defaultStateValue = ProfileState.Idle,
) {
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

    fun showEditingNotSupported() {
        viewModelScope.launch {
            mutableEvent.addEvent(Err(UiError.Res(R.string.profile_edit_not_implemented_error)))
        }
    }

    companion object {
        private const val STATE = "PROFILE_STATE"
    }
}
