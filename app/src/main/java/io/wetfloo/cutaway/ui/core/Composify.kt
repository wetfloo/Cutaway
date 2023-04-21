package io.wetfloo.cutaway.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import io.wetfloo.cutaway.core.ui.compose.core.AppTheme
import io.wetfloo.cutaway.ui.component.TransparentSystemBars

inline fun ComposeView.composify(crossinline content: @Composable () -> Unit) {
    setViewCompositionStrategy(
        strategy = ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed,
    )
    setContent {
        TransparentSystemBars()
        AppTheme {
            content()
        }
    }
}
