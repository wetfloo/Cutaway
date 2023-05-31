package io.wetfloo.cutaway.ui.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.journeyapps.barcodescanner.ScanContract
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.core.actions.defaultScanOptions
import io.wetfloo.cutaway.ui.core.actions.onQr
import io.wetfloo.cutaway.ui.core.model.DrawerAction
import kotlinx.coroutines.launch

@Composable
fun HostScaffold(
    modifier: Modifier = Modifier,
    navController: () -> NavController,
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    onLogout: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val activeDestinationId by remember {
        derivedStateOf {
            navController().currentDestination?.id
        }
    }

    val qrScanner = rememberLauncherForActivityResult(contract = ScanContract()) { scanIntentResult ->
        onQr(
            context = context,
            onError = { error ->
                // TODO
            },
            result = scanIntentResult,
        )
    }
    val drawerActions = remember {
        listOf(
            DrawerAction(textId = R.string.qr_scanner_destination_name) {
                qrScanner.launch(defaultScanOptions)
            }
        )
    }
    val drawerActionsBottom = remember {
        listOf(
            DrawerAction(
                textId = R.string.log_out_destination_name,
                action = onLogout,
            )
        )
    }

    Drawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerActions = drawerActions,
        drawerActionsBottom = drawerActionsBottom,
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
        isDestinationActive = { destination ->
            destination.id == activeDestinationId
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
