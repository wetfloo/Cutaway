package io.wetfloo.cutaway.ui.feature.createeditprofile.state

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import kotlinx.parcelize.Parcelize

@Immutable
sealed interface CreateEditProfileState : Parcelable {
    @Parcelize
    object Idle : CreateEditProfileState

    @Parcelize
    data class Available(val profileInformation: ProfileInformation) : CreateEditProfileState
}
