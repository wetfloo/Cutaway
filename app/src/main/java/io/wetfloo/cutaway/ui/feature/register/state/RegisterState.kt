package io.wetfloo.cutaway.ui.feature.register.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface RegisterState : Parcelable {
    @Parcelize
    object Idle : RegisterState

    @Parcelize
    object Loading : RegisterState
}
