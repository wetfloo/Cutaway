package io.wetfloo.cutaway.ui.component

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import io.wetfloo.cutaway.ui.destinations
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    navController: NavController,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        modifier = modifier
            .fillMaxSize(),
        content = content,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                val activeDestinationId = remember {
                    navController.currentDestination?.id
                }
                val coroutineScope = rememberCoroutineScope()

                destinations.forEach { (destinationId, textId) ->
                    val isCurrentlyActive = remember {
                        destinationId == activeDestinationId
                    }

                    NavigationDrawerItem(
                        label = {
                            Text(stringResource(textId))
                        },
                        selected = isCurrentlyActive,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()

                                if (!isCurrentlyActive) {
                                    navController.navigate(destinationId)
                                }
                            }
                        },
                    )
                }
            }
        },
    )
}

@Immutable
data class DrawerMenuItem(
    @IdRes val destinationId: Int,
    @StringRes val text: Int,
)
