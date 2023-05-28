package io.wetfloo.cutaway.ui.feature.profiledetails.state

import android.os.Parcelable
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import kotlinx.parcelize.Parcelize

sealed interface ProfileDetailedState : Parcelable {
    @Parcelize
    object Idle : ProfileDetailedState

    @Parcelize
    object Loading : ProfileDetailedState

    @Parcelize
    data class Ready(
        val profileInformation: ProfileInformation,
        val isLoading: Boolean = false,
    ) : ProfileDetailedState
}
