package io.wetfloo.cutaway.ui.feature.profile.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface ProfileState : Parcelable {
    @Parcelize
    object Idle : ProfileState

    @Parcelize
    object Loading : ProfileState

    @Parcelize
    data class Data(
        val name: String,
        val status: String?,
        val pictureUrl: String?,
        val isUpdating: Boolean = false,
    ) : ProfileState
}
