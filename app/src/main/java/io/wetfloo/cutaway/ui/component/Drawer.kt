package io.wetfloo.cutaway.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    items: List<DrawerMenuItem>,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        modifier = modifier,
        content = content,
        drawerContent = {
            ModalDrawerSheet {
                items.forEach { (text, isActive, action) ->
                    NavigationDrawerItem(
                        label = {
                            Text(text)
                        },
                        selected = isActive,
                        onClick = action,
                    )
                }
            }
        },
    )
}

@Immutable
data class DrawerMenuItem(
    val text: String,
    val isActive: Boolean = false,
    val action: () -> Unit,
)
