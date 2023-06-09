package io.wetfloo.cutaway.ui.feature.searchprofile.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.data.model.searchprofile.SearchHistoryItem
import io.wetfloo.cutaway.ui.component.BoxLoadingIndicator
import io.wetfloo.cutaway.ui.feature.searchprofile.state.SearchHistoryState

@Composable
fun SearchHistoryContent(
    modifier: Modifier = Modifier,
    onItemClick: (SearchHistoryItem) -> Unit,
    onDeleteClick: (SearchHistoryItem) -> Unit,
    onClearClick: () -> Unit,
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
                AnimatedVisibility(
                    visible = state.data.isEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = stringResource(R.string.search_profile_history_empty))
                    }
                }

                AnimatedVisibility(
                    visible = state.data.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(top = dimensionResource(R.dimen.default_padding)),
                            modifier = Modifier
                                .weight(1f),
                        ) {
                            item {
                                Button(
                                    onClick = onClearClick,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = dimensionResource(R.dimen.default_padding))
                                        .widthIn(max = dimensionResource(R.dimen.max_bar_width)),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null,
                                    )
                                    Text(text = stringResource(R.string.search_profile_clear_history))
                                }
                            }

                            items(items = state.data) { history ->
                                SearchHistoryItem(
                                    history = history,
                                    onDeleteClick = { onDeleteClick(history) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .requiredHeightIn(min = dimensionResource(R.dimen.list_item_min_height))
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
    }
}
