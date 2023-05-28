package io.wetfloo.cutaway.ui.feature.searchprofile.state

import io.wetfloo.cutaway.data.model.searchprofile.SearchHistoryItem

sealed interface SearchHistoryState {
    object Idle : SearchHistoryState
    object Loading : SearchHistoryState
    data class Ready(val data: List<SearchHistoryItem>) : SearchHistoryState
}
