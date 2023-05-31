package io.wetfloo.cutaway.ui.feature.searchprofile.state

import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.data.model.searchprofile.SearchHistoryItem

sealed interface SearchProfileMessage {
    object SearchRequested : SearchProfileMessage

    object ClearHistory : SearchProfileMessage

    data class DeleteHistoryItem(val item: SearchHistoryItem) : SearchProfileMessage

    data class FoundProfileClicked(val item: ProfileInformation) : SearchProfileMessage

    object Logout : SearchProfileMessage
}
