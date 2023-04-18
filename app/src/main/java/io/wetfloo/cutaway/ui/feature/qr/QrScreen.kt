package io.wetfloo.cutaway.ui.feature.qr

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun QrScreen(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Text(
            text = "qr code scanner will arrive soon™",
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
