package io.wetfloo.cutaway.ui.feature.searchuser.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.wetfloo.cutaway.ui.component.BoxLoadingIndicator
import io.wetfloo.cutaway.ui.feature.searchuser.state.SearchHistoryState

@Composable
fun SearchHistoryContent(
    modifier: Modifier = Modifier,
    state: SearchHistoryState,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
    ) {
        when (state) {
            SearchHistoryState.Idle -> Unit
            SearchHistoryState.Loading -> BoxLoadingIndicator()
            is SearchHistoryState.Ready -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    items(items = state.data) { history ->
                        SearchHistoryItem(history = history)
                    }
                }
            }
        }
    }
}
