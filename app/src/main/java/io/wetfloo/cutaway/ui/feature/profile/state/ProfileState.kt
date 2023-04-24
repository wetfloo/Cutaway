package io.wetfloo.cutaway.ui.feature.profile.state

import android.os.Parcelable
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import kotlinx.parcelize.Parcelize

sealed interface ProfileState : Parcelable {
    @Parcelize
    object Idle : ProfileState

    @Parcelize
    object Loading : ProfileState

    @Parcelize
    data class Ready(
        val data: ProfileInformation,
        val isUpdating: Boolean = false,
    ) : ProfileState
}
