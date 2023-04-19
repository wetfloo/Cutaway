package io.wetfloo.cutaway.ui.feature.qrgenerator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun QrGeneratorScreen(
    qrGeneratorState: QrGeneratorState,
) {
    if (qrGeneratorState.isLoading) {
        CircularProgressIndicator() // TODO this is huge for some reason
    } else {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = qrGeneratorState.qrBitmap,
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
    }
}
