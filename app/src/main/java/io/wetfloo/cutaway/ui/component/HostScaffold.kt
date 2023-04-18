package io.wetfloo.cutaway.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostScaffold(
    modifier: Modifier = Modifier,
    navController: NavController,
    title: String,
    content: @Composable (PaddingValues) -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val activeDestinationId = remember {
        navController.currentDestination?.id
    }

    Drawer(
        modifier = modifier,
        drawerState = drawerState,
        isActive = { destinationId ->
            destinationId == activeDestinationId
        },
        onDestinationClick = { destinationId ->
            coroutineScope.launch {
                drawerState.close()

                if (destinationId != activeDestinationId) {
                    navController.navigate(destinationId)
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
                )
            },
        ) { paddingValues ->
            content(paddingValues)
        }
    }
}
