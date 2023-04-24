package io.wetfloo.cutaway.core.commonimpl

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.wetfloo.cutaway.core.common.eventflow.MutableEventFlow
import kotlinx.coroutines.flow.StateFlow

abstract class StateViewModel<S : Parcelable, V>(
    protected val savedStateHandle: SavedStateHandle,
    protected val savedStateKey: String,
    private val defaultStateValue: S,
) : ViewModel() {
    val state: StateFlow<S> = savedStateHandle.getStateFlow(
        key = savedStateKey,
        initialValue = defaultStateValue,
    )

    var stateValue
        get() = state.value
        protected set(value) {
            savedStateHandle[savedStateKey] = value
        }

    protected val mutableEvent: MutableEventResultFlow<V> = MutableEventFlow()
    val event = mutableEvent.asEventFlow()

    protected fun updateState(updater: (S) -> S) {
        stateValue = updater(stateValue)
    }
}
