package io.wetfloo.cutaway.ui.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Err
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.common.eventflow.MutableEventFlow
import io.wetfloo.cutaway.core.commonimpl.MutableEventResultFlow
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.core.commonimpl.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val profileState = savedStateHandle.getStateFlow(
        key = PROFILE_STATE,
        initialValue = ProfileState(),
    )

    private val _profileEvent: MutableEventResultFlow<ProfileEvent> = MutableEventFlow()
    val profileEvent = _profileEvent.asEventFlow()

    fun showEditingNotSupported() {
        viewModelScope.launch {
            _profileEvent.addEvent(Err(UiError.Resource(R.string.profile_edit_not_implemented_error)))
        }
    }

    private inline fun updateState(block: (ProfileState) -> ProfileState) {
        savedStateHandle.update(
            key = PROFILE_STATE,
            defaultValue = ProfileState(),
            updater = block,
        )
    }

    companion object {
        private const val PROFILE_STATE = "PROFILE_STATE"
    }
}
