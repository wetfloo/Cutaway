package io.wetfloo.cutaway.ui.feature.createeditprofile.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface CreateEditProfileState : Parcelable {
    @Parcelize
    object Idle : CreateEditProfileState
}
