package io.wetfloo.cutaway.ui.feature.profile

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
    fun reload(profileId: String?) {
        when (val currentState = stateValue) {
            is ProfileState.Ready -> viewModelScope.launch {
                handle(
                    loadingValue = currentState.copy(isUpdating = true),
                    id = profileId,
                )
            }

            ProfileState.Idle -> viewModelScope.launch {
                handle(
                    loadingValue = ProfileState.Loading,
                    id = profileId,
                )
            }

            ProfileState.Loading -> return
        }
    }

    /**
     * Loads profile information for the first time,
     * does nothing if it's already `Ready` or `Loading`
     */
    fun load(profileId: String?) {
        when (stateValue) {
            ProfileState.Idle -> reload(profileId)
            ProfileState.Loading, is ProfileState.Ready -> return
        }
    }

    private suspend fun updateProfile(id: String?): Result<ProfileState.Ready, UiError> {
        val profileInformation = if (id != null) {
            profileRepository.loadProfileInformation(id)
        } else {
            profileRepository.loadMyProfileInformation()
        }

        return profileInformation
            .map(ProfileState::Ready)
            .mapError { UiError.Res(R.string.profile_failure_load) }
    }

    private suspend fun handle(
        loadingValue: ProfileState,
        id: String?,
    ) {
        handleStateResult(
            previousValue = stateValue,
            loadingValue = loadingValue,
            valueReceiver = { stateValue = it },
            errorReceiver = { _error.send(it) },
        ) {
            updateProfile(id)
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
