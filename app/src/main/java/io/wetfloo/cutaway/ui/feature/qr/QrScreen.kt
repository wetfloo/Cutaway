package io.wetfloo.cutaway.ui.feature.qr

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.component.EventFlowSnackbarDisplay
import io.wetfloo.cutaway.ui.component.HostScaffold
import kotlinx.coroutines.flow.Flow

@Composable
fun QrScreen(
    navController: () -> NavController,
    errorFlow: Flow<UiError>,
    modifier: Modifier = Modifier,
) {
    EventFlowSnackbarDisplay(errorFlow = errorFlow) { snackbarHostState ->
        HostScaffold(
            navController = navController,
            modifier = modifier
                .fillMaxSize(),
            title = stringResource(R.string.qr_scanner_destination_name),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) { scaffoldPaddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPaddingValues),
            ) {
                Text(
                    text = "qr code scanner will arrive soon™",
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}
