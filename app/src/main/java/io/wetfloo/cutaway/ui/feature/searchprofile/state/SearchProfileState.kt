package io.wetfloo.cutaway.ui.feature.searchprofile.state

import android.os.Parcelable
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import kotlinx.parcelize.Parcelize

sealed interface SearchProfileState : Parcelable {
    @Parcelize
    object Idle : SearchProfileState

    @Parcelize
    object Loading : SearchProfileState

    @Parcelize
    data class Found(
        val profiles: List<ProfileInformation>,
        val isLoading: Boolean = false,
    ) : SearchProfileState
}
