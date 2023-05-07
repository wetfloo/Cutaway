package io.wetfloo.cutaway.ui.feature.searchuser.state

import io.wetfloo.cutaway.data.model.searchuser.SearchHistoryItem

sealed interface SearchUserMessage {
    object SearchRequested : SearchUserMessage
    data class DeleteHistoryItem(val item: SearchHistoryItem) : SearchUserMessage
}
