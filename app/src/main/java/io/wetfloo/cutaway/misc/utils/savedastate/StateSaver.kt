package io.wetfloo.cutaway.misc.utils.savedastate

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.runCatching
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KProperty

class StateSaver<S : Parcelable>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    defaultState: S,
) {
    val state: StateFlow<S> = savedStateHandle.getStateFlow(
        key = key,
        initialValue = defaultState,
    )

    fun save(value: S): Result<S, Throwable> = runCatching {
        savedStateHandle.set(
            key = key,
            value = value,
        )
    }.map { value }

    inline fun update(block: (S) -> S): Result<S, Throwable> {
        val oldValue = state.value
        val newValue = block(oldValue)
        return save(newValue)
    }

    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): S = state.value

    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: S,
    ) {
        save(value)
    }
}
