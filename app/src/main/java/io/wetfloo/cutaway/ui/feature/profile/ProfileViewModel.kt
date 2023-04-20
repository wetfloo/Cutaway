package io.wetfloo.cutaway.ui.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Err
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.common.SAMPLE_PROFILE_PICTURE_URL
import io.wetfloo.cutaway.core.commonimpl.StateViewModel
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileEvent
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : StateViewModel<ProfileState, ProfileEvent>(
    savedStateHandle = savedStateHandle,
    savedStateKey = STATE,
    defaultStateValue = ProfileState.Idle,
) {
    fun load() {
        when (val currentState = stateValue) {
            is ProfileState.Data -> {
                stateValue = currentState.copy(
                    isUpdating = true,
                )

                viewModelScope.launch {
                    stateValue = updateProfile()
                }
            }

            ProfileState.Idle -> viewModelScope.launch {
                stateValue = ProfileState.Loading
                stateValue = updateProfile()
            }

            ProfileState.Loading -> state
        }
    }

    private suspend fun updateProfile(): ProfileState.Data {
        delay(3000)
        return ProfileState.Data(
            name = "After Dark",
            status = "Reach for the sky, sinner!",
            pictureUrl = SAMPLE_PROFILE_PICTURE_URL,
        )
    }

    fun showEditingNotSupported() {
        viewModelScope.launch {
            mutableEvent.addEvent(Err(UiError.Resource(R.string.profile_edit_not_implemented_error)))
        }
    }

    companion object {
        private const val STATE = "PROFILE_STATE"
    }
}
