package io.wetfloo.cutaway.ui.feature.createeditprofile

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.Res
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.core.commonimpl.logW
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.data.repository.createeditprofile.CreateEditProfileRepository
import io.wetfloo.cutaway.misc.utils.savedastate.StateSaver
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditMode
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditProfileEvent
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditProfileState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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

    private val _event: Channel<CreateEditProfileEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun initProfile(mode: CreateEditMode) {
        when (mode) {
            CreateEditMode.Create -> writeProfile(ProfileInformation.empty)

            is CreateEditMode.Edit -> {
                if (stateValue !is CreateEditProfileState.Available) {
                    writeProfile(mode.profileInformation)
                }
            }
        }
    }

    fun updateProfile(profileInformation: ProfileInformation) {
        writeProfile(profileInformation)
    }

    private fun writeProfile(profileInformation: ProfileInformation) {
        stateValue = when (val value = stateValue) {
            is CreateEditProfileState.Available -> value.copy(
                profileInformation = profileInformation,
            )

            CreateEditProfileState.Idle -> CreateEditProfileState.Available(
                profileInformation = profileInformation,
            )
        }
    }

    fun imagePicked(uri: Uri) {
        when (val value = stateValue) {
            is CreateEditProfileState.Available -> {
                stateValue = value.copy(
                    pictureUri = uri,
                )
            }

            CreateEditProfileState.Idle -> error("It shouldn't be possible to pick images while we're idle.")
        }
    }

    fun commitUpdate(mode: CreateEditMode) {
        val value = stateValue
        require(value is CreateEditProfileState.Available) {
            "Tried to update the profile before it became available. Blazingly fast!"
        }
        val profileInformation = value.profileInformation
        if (!profileInformation.isValid) {
            return
        }

        viewModelScope.launch {
            val profilePictureUri = (stateValue as? CreateEditProfileState.Available)?.pictureUri
            val uploadPictureResult = if (profilePictureUri != null) {
                createEditProfileRepository.uploadImage(profilePictureUri)
            } else null
            val profilePictureId = uploadPictureResult?.get()
            val newProfileInformation = if (profilePictureId != null) {
                profileInformation.copy(
                    profilePictureId = profilePictureId,
                )
            } else profileInformation

            when (mode) {
                CreateEditMode.Create -> createEditProfileRepository
                    .createProfile(profileInformation = newProfileInformation)
                    .handle()

                is CreateEditMode.Edit -> createEditProfileRepository
                    .updateProfile(profileInformation = newProfileInformation)
                    .handle()
            }
        }
    }

    private val ProfileInformation.isValid
        get() = name.isNotEmpty()

    private suspend fun Result<*, Throwable>.handle() {
        onSuccess {
            _event.send(CreateEditProfileEvent.Saved)
        }.onFailure {
            _error.send(
                Res(R.string.create_edit_profile_destination_failure_generic)
            )
        }.logW(TAG)
    }

    companion object {
        private const val TAG = "EditProfileViewModel"
    }
}
