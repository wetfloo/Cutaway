package io.wetfloo.cutaway.ui.feature.searchuser.state

import io.wetfloo.cutaway.data.model.searchuser.SearchHistoryItem

sealed interface SearchHistoryState {
    object Idle : SearchHistoryState
    object Loading : SearchHistoryState
    data class Ready(val data: List<SearchHistoryItem>) : SearchHistoryState
}
