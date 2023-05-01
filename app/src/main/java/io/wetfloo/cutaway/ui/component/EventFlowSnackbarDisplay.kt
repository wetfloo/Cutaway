package io.wetfloo.cutaway.ui.component

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import io.wetfloo.cutaway.core.commonimpl.UiError
import kotlinx.coroutines.flow.Flow

@Composable
fun EventFlowSnackbarDisplay(
    errorFlow: Flow<UiError>,
    content: @Composable (SnackbarHostState) -> Unit,
) {
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        errorFlow.collect { error ->
            snackbarHostState.showSnackbar(
                message = error.errorString(context),
            )
        }
    }

    content(snackbarHostState)
}
