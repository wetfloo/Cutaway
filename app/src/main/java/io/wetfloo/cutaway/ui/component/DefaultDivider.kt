package io.wetfloo.cutaway.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Divider with actually matching colors for background.
 * Its default alpha value allows it to be quite subtle.
 *
 * Note that [padding] is only applied horizontally.
 */
@Composable
fun DefaultDivider(
    modifier: Modifier = Modifier,
    padding: Dp = 8.dp,
    alpha: Float = .3f,
) {
    val contentColor = LocalContentColor.current
    Divider(
        modifier = modifier
            .padding(
                horizontal = padding,
            ),
        color = contentColor.copy(
            alpha = alpha,
        ),
    )
}
