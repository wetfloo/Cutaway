package io.wetfloo.cutaway.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.navOptions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostScaffold(
    modifier: Modifier = Modifier,
    navController: () -> NavController,
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val activeDestinationId by remember {
        derivedStateOf {
            navController().currentDestination?.id
        }
    }

    Drawer(
        modifier = modifier,
        drawerState = drawerState,
        isDestinationActive = { destination ->
            destination.id == activeDestinationId
        },
        onDestinationClick = { destination ->
            coroutineScope.launch {
                drawerState.close()

                if (destination.id != activeDestinationId) {
                    navController().navigate(
                        resId = destination.id,
                        args = null,
                        navOptions = navOptions {
                            activeDestinationId?.let {
                                popUpTo(it) {
                                    inclusive = true
                                }
                            }
                        },
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                HostTopAppBar(
                    text = title,
                    onMenuClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    },
                    actions = actions,
                )
            },
            snackbarHost = snackbarHost,
        ) { paddingValues ->
            content(paddingValues)
        }
    }
}
