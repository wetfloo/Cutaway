package io.wetfloo.cutaway.misc.utils.savedastate

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow

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
}
