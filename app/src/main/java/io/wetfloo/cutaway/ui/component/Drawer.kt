package io.wetfloo.cutaway.ui.component

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.wetfloo.cutaway.core.common.forEachInBetween
import io.wetfloo.cutaway.ui.core.destinations
import io.wetfloo.cutaway.ui.core.model.DrawerDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    onDestinationClick: (DrawerDestination) -> Unit,
    isDestinationActive: (DrawerDestination) -> Boolean,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        modifier = modifier,
        content = content,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                destinations.asIterable().forEachInBetween(
                    inBetweenBlock = {
                        Spacer(Modifier.height(2.dp))
                    },
                ) { destination ->
                    val isActive by remember {
                        derivedStateOf {
                            isDestinationActive(destination)
                        }
                    }

                    NavigationDrawerItem(
                        selected = isActive,
                        label = {
                            Text(stringResource(destination.textId))
                        },
                        onClick = {
                            onDestinationClick(destination)
                        },
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
