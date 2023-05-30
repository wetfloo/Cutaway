package io.wetfloo.cutaway.ui.feature.createeditprofile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.toResultOr
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.core.common.DispatcherProvider
import io.wetfloo.cutaway.core.commonimpl.UiError
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
@OptIn(SavedStateHandleSaveableApi::class)
class CreateEditProfileViewModel @Inject constructor(
    private val createEditProfileRepository: CreateEditProfileRepository,
    private val dispatchers: DispatcherProvider,
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
        val newState = CreateEditProfileState.Available(profileInformation)
        stateValue = newState
    }

    fun commitUpdate() {
        viewModelScope.launch {
            Unit
                .toResultOr {
                    UiError.Raw("idk")
                }
                .onSuccess {
                    _event.send(CreateEditProfileEvent.Saved)
                }
        }
    }

    companion object {
        private const val TAG = "EditProfileViewModel"
    }
}
