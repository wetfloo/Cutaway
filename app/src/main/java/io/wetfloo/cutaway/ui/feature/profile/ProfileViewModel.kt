package io.wetfloo.cutaway.ui.feature.profile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.core.commonimpl.handleStateResult
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

    /**
     * Reloads profile information, even if it's present
     */
    fun reload() {
        when (val currentState = stateValue) {
            is ProfileState.Ready -> viewModelScope.launch {
                handle(loadingValue = currentState.copy(isUpdating = true))
            }

            ProfileState.Idle -> viewModelScope.launch {
                handle(loadingValue = ProfileState.Loading)
            }

            ProfileState.Loading -> return
        }
    }

    /**
     * Loads profile information for the first time,
     * does nothing if it's already `Ready` or `Loading`
     */
    fun load() {
        when (stateValue) {
            ProfileState.Idle -> reload()
            ProfileState.Loading, is ProfileState.Ready -> return
        }
    }

    private suspend fun updateProfile(): Result<ProfileState.Ready, UiError> =
        profileRepository
            .loadProfileInformation()
            .map(ProfileState::Ready)
            .mapError {
                Log.w(TAG, it)
                UiError.Res(R.string.profile_failure_load)
            }

    private suspend fun handle(loadingValue: ProfileState) {
        handleStateResult(
            previousValue = stateValue,
            loadingValue = loadingValue,
            valueReceiver = { stateValue = it },
            errorReceiver = {
                _error.send(it)
            },
        ) {
            updateProfile()
        }
    }

    fun showEditingNotSupported() {
        viewModelScope.launch {
            _error.send(UiError.Res(R.string.profile_edit_not_implemented_error))
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}
