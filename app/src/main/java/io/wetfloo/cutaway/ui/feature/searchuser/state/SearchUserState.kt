package io.wetfloo.cutaway.ui.feature.searchuser.state

import android.os.Parcelable
import io.wetfloo.cutaway.data.model.searchuser.FoundUser
import kotlinx.parcelize.Parcelize

sealed interface SearchUserState : Parcelable {
    @Parcelize
    object Idle : SearchUserState

    @Parcelize
    object Loading : SearchUserState

    @Parcelize
    data class Found(
        val users: List<FoundUser>,
        val isLoading: Boolean = false,
    ) : SearchUserState
}
