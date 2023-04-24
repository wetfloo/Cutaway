package io.wetfloo.cutaway.ui.feature.auth.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface AuthState : Parcelable {
    @Parcelize
    object Idle : AuthState

    @Parcelize
    object Loading : AuthState
}
