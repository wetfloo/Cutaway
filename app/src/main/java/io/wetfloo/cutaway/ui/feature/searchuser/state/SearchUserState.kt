package io.wetfloo.cutaway.ui.feature.searchuser.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface SearchUserState : Parcelable {
    @Parcelize
    object Idle : SearchUserState
}
