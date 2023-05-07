package io.wetfloo.cutaway.ui.component

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.wetfloo.cutaway.core.common.forEachInBetween
import io.wetfloo.cutaway.ui.core.destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    onDestinationClick: (Int) -> Unit,
    isActive: (Int) -> Boolean,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        modifier = modifier,
        content = content,
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet {
                destinations.forEachInBetween(
                    inBetweenBlock = {
                        Spacer(Modifier.height(2.dp))
                    },
                ) { (destinationId, textId) ->
                    NavigationDrawerItem(
                        label = {
                            Text(stringResource(textId))
                        },
                        onClick = {
                            onDestinationClick(destinationId)
                        },
                        selected = isActive(destinationId),
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            top = 8.dp,
                        ),
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
