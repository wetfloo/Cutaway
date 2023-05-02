package io.wetfloo.cutaway.ui.feature.searchuser

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.component.EventFlowSnackbarDisplay
import io.wetfloo.cutaway.ui.component.HostScaffold
import io.wetfloo.cutaway.ui.feature.searchuser.state.SearchUserState
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchUserScreen(
    state: SearchUserState,
    errorFlow: Flow<UiError>,
    navController: () -> NavController,
    onQueryChange: (String) -> Unit,
    queryValue: String,
) {
    EventFlowSnackbarDisplay(errorFlow = errorFlow) { snackbarHostState ->
        HostScaffold(
            navController = navController,
            title = stringResource(R.string.search_user_destination_name),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) { scaffoldPaddingValues ->
            Column(
                modifier = Modifier
                    .padding(scaffoldPaddingValues),
            ) {
                TextField(
                    value = queryValue,
                    onValueChange = onQueryChange,
                )
                Text("WIP")

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    when (state) {
                        is SearchUserState.Found -> {
                            Column {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                ) {
                                    items(items = state.users) { user ->
                                        SearchUserItem(user = user)
                                    }
                                }
                            }
                        }

                        SearchUserState.Idle -> Unit

                        SearchUserState.Loading -> CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center),
                        )
                    }
                }
            }
        }
    }
}
