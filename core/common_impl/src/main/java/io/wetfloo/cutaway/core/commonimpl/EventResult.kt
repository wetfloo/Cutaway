package io.wetfloo.cutaway.core.commonimpl

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess

@Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
suspend inline fun <V, E> handleStateResult(
    previousValue: V,
    loadingValue: V,
    valueReceiver: (V) -> Unit,
    errorReceiver: suspend (E) -> Unit,
    operation: () -> Result<V, E>,
) {
    valueReceiver(loadingValue)

    operation()
        .onSuccess(valueReceiver)
        .onFailure { error ->
            valueReceiver(previousValue)
            errorReceiver(error)
        }
}
