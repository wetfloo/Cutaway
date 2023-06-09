package io.wetfloo.cutaway.ui.feature.profiledetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.data.repository.profile.ProfileRepository
import io.wetfloo.cutaway.misc.utils.savedastate.StateSaver
import io.wetfloo.cutaway.ui.feature.profiledetails.state.ProfileDetailedState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDetailedInformationViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val stateSaver = StateSaver<ProfileDetailedState>(
        savedStateHandle = savedStateHandle,
        key = "PROFILE_DETAILS_STATE",
        defaultState = ProfileDetailedState.Idle,
    )
    private var stateValue by stateSaver
    val stateFlow = stateSaver.state

    fun initProfile(profileInformation: ProfileInformation) {
        if (stateValue !is ProfileDetailedState.Ready) {
            stateValue = ProfileDetailedState.Ready(profileInformation = profileInformation)
        }
    }

    fun loadProfileInformationById(id: String) {
        stateValue = ProfileDetailedState.Loading
        viewModelScope.launch {
            profileRepository
                .loadProfileInformation(id)
                .onSuccess { profileInformation ->
                    stateValue = ProfileDetailedState.Ready(profileInformation)
                }
        }
    }
}
