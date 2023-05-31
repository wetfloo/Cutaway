@file:OptIn(ExperimentalMaterial3Api::class)

package io.wetfloo.cutaway.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
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
import io.wetfloo.cutaway.ui.core.InBetween
import io.wetfloo.cutaway.ui.core.destinations
import io.wetfloo.cutaway.ui.core.model.DrawerAction
import io.wetfloo.cutaway.ui.core.model.DrawerDestination

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    onDestinationClick: (DrawerDestination) -> Unit,
    isDestinationActive: (DrawerDestination) -> Boolean,
    drawerActions: List<DrawerAction> = emptyList(),
    drawerActionsBottom: List<DrawerAction> = emptyList(),
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        modifier = modifier,
        content = content,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                InBetween(
                    items = destinations.asIterable(),
                    inBetweenBlock = { DefaultSpacer() }
                ) { item ->
                    val isActive by remember {
                        derivedStateOf {
                            isDestinationActive(item)
                        }
                    }

                    DefaultNavigationItem(
                        selected = isActive,
                        label = item.textId,
                        onClick = {
                            onDestinationClick(item)
                        },
                    )
                }

                InBetween(
                    items = drawerActions,
                    inBetweenBlock = { DefaultSpacer() },
                ) { item ->
                    DefaultNavigationItem(
                        selected = false,
                        label = item.textId,
                        onClick = {
                            item.action()
                        },
                    )
                }

                Spacer(
                    modifier = Modifier
                        .weight(1f),
                )

                InBetween(
                    items = drawerActionsBottom,
                    inBetweenBlock = { DefaultSpacer() },
                ) { item ->
                    DefaultNavigationItem(
                        selected = false,
                        label = item.textId,
                        onClick = {
                            item.action()
                        },
                    )
                }
            }
        },
    )
}

@Composable
private fun DefaultSpacer() {
    SpacerSized(h = 2.dp)
}

@Composable
private fun DefaultNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    @StringRes label: Int,
) {
    NavigationDrawerItem(
        selected = selected,
        label = {
            Text(stringResource(label))
        },
        onClick = onClick,
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp,
            top = 8.dp,
        ),
    )
}
