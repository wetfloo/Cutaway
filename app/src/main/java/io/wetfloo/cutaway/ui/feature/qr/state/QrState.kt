package io.wetfloo.cutaway.ui.feature.qr.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface QrState : Parcelable {
    @Parcelize
    object Idle : QrState
}
