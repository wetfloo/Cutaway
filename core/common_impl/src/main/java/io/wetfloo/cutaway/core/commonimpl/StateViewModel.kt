package io.wetfloo.cutaway.core.commonimpl

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.wetfloo.cutaway.core.common.eventflow.MutableEventFlow
import kotlinx.coroutines.flow.StateFlow

abstract class StateViewModel<T : Parcelable, V>(
    protected val savedStateHandle: SavedStateHandle,
    private val savedStateKey: String,
    private val defaultStateValue: T,
) : ViewModel() {
    val state: StateFlow<T> = savedStateHandle.getStateFlow(
        key = savedStateKey,
        initialValue = defaultStateValue,
    )

    protected val mutableEvent: MutableEventResultFlow<V> = MutableEventFlow()
    val event = mutableEvent.asEventFlow()

    protected fun updateState(updater: (T) -> T) {
        savedStateHandle.update(
            key = savedStateKey,
            defaultValue = defaultStateValue,
            updater = updater,
        )
    }
}
