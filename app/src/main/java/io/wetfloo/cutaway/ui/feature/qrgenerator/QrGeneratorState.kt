package io.wetfloo.cutaway.ui.feature.qrgenerator

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable

@Immutable
data class QrGeneratorState(
    val isLoading: Boolean = false,
    val qrBitmap: Bitmap? = null,
)
