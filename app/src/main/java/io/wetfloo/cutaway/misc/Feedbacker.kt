package io.wetfloo.cutaway.misc

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Feedbacker @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    @Suppress("DEPRECATION")
    fun feedback() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
            vibrator.vibrate(effect)
        } else {
            vibrator.vibrate(DEFAULT_TIME)
        }
    }

    companion object {
        private const val DEFAULT_TIME = 30L
    }
}
