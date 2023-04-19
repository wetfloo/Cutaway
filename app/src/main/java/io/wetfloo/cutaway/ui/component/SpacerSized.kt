package io.wetfloo.cutaway.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpacerSized(
    w: Dp = 0.dp,
    h: Dp = 0.dp,
) {
    Spacer(
        modifier = Modifier.size(
            width = w,
            height = h,
        ),
    )
}
