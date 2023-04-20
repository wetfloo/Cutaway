package io.wetfloo.cutaway.ui.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Err
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.StateViewModel
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileEvent
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : StateViewModel<ProfileState, ProfileEvent>(
    savedStateHandle = savedStateHandle,
    savedStateKey = STATE,
    defaultStateValue = ProfileState(),
) {
    fun showEditingNotSupported() {
        viewModelScope.launch {
            mutableEvent.addEvent(Err(UiError.Resource(R.string.profile_edit_not_implemented_error)))
        }
    }

    companion object {
        private const val STATE = "PROFILE_STATE"
    }
}
