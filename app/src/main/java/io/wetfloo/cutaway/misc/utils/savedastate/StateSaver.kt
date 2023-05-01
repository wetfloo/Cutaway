package io.wetfloo.cutaway.misc.utils.savedastate

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
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

    fun save(value: S) {
        savedStateHandle.set(
            key = key,
            value = value,
        )
    }

    inline fun update(block: (S) -> S) {
        val oldValue = state.value
        val newValue = block(oldValue)
        save(newValue)
    }

    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): S = state.value

    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: S
    ) {
        save(value)
    }
}
