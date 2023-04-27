package io.wetfloo.cutaway.ui.component

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.onFailure
import io.wetfloo.cutaway.core.commonimpl.EventResultFlow

@Composable
fun <T> EventFlowSnackbarDisplay(
    eventFlow: EventResultFlow<T>,
    content: @Composable (SnackbarHostState) -> Unit,
) {
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        eventFlow.consumeAndNotify { eventResult ->
            eventResult.onFailure { error ->
                snackbarHostState.showSnackbar(
                    message = error.errorString(context),
                )
            }

            eventResult is Err
        }
    }

    content(snackbarHostState)
}
