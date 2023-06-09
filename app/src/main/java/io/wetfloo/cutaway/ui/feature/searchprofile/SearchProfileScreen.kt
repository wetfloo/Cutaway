package io.wetfloo.cutaway.ui.feature.searchprofile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.component.EventFlowSnackbarDisplay
import io.wetfloo.cutaway.ui.component.HostScaffold
import io.wetfloo.cutaway.ui.core.model.KeyboardVisibilityAction
import io.wetfloo.cutaway.ui.feature.searchprofile.component.SearchHistoryContent
import io.wetfloo.cutaway.ui.feature.searchprofile.component.SearchProfileContent
import io.wetfloo.cutaway.ui.feature.searchprofile.state.SearchHistoryState
import io.wetfloo.cutaway.ui.feature.searchprofile.state.SearchPagerTab
import io.wetfloo.cutaway.ui.feature.searchprofile.state.SearchProfileMessage
import io.wetfloo.cutaway.ui.feature.searchprofile.state.SearchProfileState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun SearchProfileScreen(
    state: SearchProfileState,
    searchHistoryState: SearchHistoryState,
    errorFlow: Flow<UiError>,
    navController: () -> NavController,
    onMessage: (SearchProfileMessage) -> Unit,
    onQueryChange: (String) -> Unit,
    queryValue: String,
) {
    EventFlowSnackbarDisplay(errorFlow = errorFlow) { snackbarHostState ->
        HostScaffold(
            navController = navController,
            title = stringResource(R.string.search_profile_destination_name),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            onLogout = {
                onMessage(SearchProfileMessage.Logout)
            },
        ) { scaffoldPaddingValues ->
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()
            val pagerState = rememberPagerState()
            val keyboardController = LocalSoftwareKeyboardController.current
            val titles = remember {
                SearchPagerTab
                    .values()
                    .map { tab ->
                        context.getString(tab.stringRes)
                    }
            }

            fun scrollToPage(index: Int) {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }

            LaunchedEffect(pagerState.currentPage) {
                val currentTab = SearchPagerTab.values()[pagerState.currentPage]
                when (currentTab.keyboardVisibilityAction) {
                    KeyboardVisibilityAction.SHOW -> keyboardController?.show()
                    KeyboardVisibilityAction.HIDE -> keyboardController?.hide()
                    KeyboardVisibilityAction.IDLE -> Unit
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPaddingValues),
            ) {
                TabRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(
                                tabPositions[pagerState.currentPage],
                            ),
                        )
                    },
                ) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { scrollToPage(index) },
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            },
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    pageCount = titles.count(),
                    modifier = Modifier
                        .fillMaxSize(),
                    pageSize = PageSize.Fill,
                ) { index ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        when (index) {
                            SearchPagerTab.SEARCH.ordinal -> {
                                SearchProfileContent(
                                    onQueryChange = onQueryChange,
                                    queryValue = queryValue,
                                    state = state,
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    onSearchRequested = {
                                        onMessage(SearchProfileMessage.SearchRequested)
                                    },
                                    onItemClicked = { profile ->
                                        onMessage(SearchProfileMessage.FoundProfileClicked(profile))
                                    },
                                )
                            }

                            SearchPagerTab.HISTORY.ordinal -> {
                                SearchHistoryContent(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    onItemClick = { history ->
                                        onQueryChange(history.query)
                                        scrollToPage(SearchPagerTab.SEARCH.ordinal)
                                    },
                                    onDeleteClick = { history ->
                                        onMessage(SearchProfileMessage.DeleteHistoryItem(history))
                                    },
                                    onClearClick = {
                                        onQueryChange("")
                                        onMessage(SearchProfileMessage.ClearHistory)
                                    },
                                    state = searchHistoryState,
                                )
                            }

                            else -> error("Illegal page index")
                        }
                    }
                }
            }

        }
    }
}
