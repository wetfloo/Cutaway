package io.wetfloo.cutaway.ui.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun TransparentSystemBars(
    useDarkIcons: Boolean = !isSystemInDarkTheme()
) {
    val systemUiController = rememberSystemUiController()

    DisposableEffect(
        systemUiController,
        useDarkIcons,
    ) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )

        onDispose {}
    }
}
