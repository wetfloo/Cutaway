package io.wetfloo.cutaway.ui.feature.searchuser.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.data.model.searchuser.SearchHistoryItem
import io.wetfloo.cutaway.ui.component.BoxLoadingIndicator
import io.wetfloo.cutaway.ui.feature.searchuser.state.SearchHistoryState

@Composable
fun SearchHistoryContent(
    modifier: Modifier = Modifier,
    onItemClick: (SearchHistoryItem) -> Unit,
    onDeleteClick: (SearchHistoryItem) -> Unit,
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
                        SearchHistoryItem(
                            history = history,
                            onDeleteClick = { onDeleteClick(history) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .sizeIn(minHeight = dimensionResource(R.dimen.list_item_min_height))
                                .clickable {
                                    onItemClick(history)
                                },
                        )
                    }
                }
            }
        }
    }
}
