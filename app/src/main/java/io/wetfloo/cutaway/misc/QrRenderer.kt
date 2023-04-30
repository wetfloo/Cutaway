package io.wetfloo.cutaway.misc

import android.graphics.Bitmap
import com.github.michaelbull.result.Result
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.option.RenderOption
import io.wetfloo.cutaway.core.common.DispatcherProvider
import io.wetfloo.cutaway.core.common.runSuspendCatching
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QrRenderer @Inject constructor(
    private val dispatchers: DispatcherProvider,
) {
    suspend fun renderQr(
        content: String,
        renderOptions: RenderOption = defaultRenderOptions(content),
    ): Result<Bitmap, Throwable> {
        return runSuspendCatching {
            withContext(dispatchers.default) {
                AwesomeQrRenderer.render(renderOptions)
            }.bitmap!!
        }
    }

    private fun defaultRenderOptions(
        content: String,
    ) = RenderOption().apply {
        this.content = content
        size = 800
    }
}
