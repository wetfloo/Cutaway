package io.wetfloo.cutaway.core.commonimpl

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.core.common.eventflow.MutableEventFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * You already know what this is. Yep, this is a base [ViewModel] :^)
 * @param savedStateKey Key to be used to store data inside of [savedStateHandle]
 * @param defaultStateValue Starting value for [state]
 * @param S Type of state
 * @param V Type of successful events that this ViewModel will send to the UI
 * @param E Type of error events that this ViewModel will send to the UI
 */
abstract class StateViewModel<S : Parcelable, V, E>(
    protected val savedStateHandle: SavedStateHandle,
    private val savedStateKey: String,
    defaultStateValue: S,
) : ViewModel() {
    val state: StateFlow<S> = savedStateHandle.getStateFlow(
        key = savedStateKey,
        initialValue = defaultStateValue,
    )

    protected val mutableEvent: MutableEventFlow<Result<V, E>> = MutableEventFlow()
    val event = mutableEvent.asEventFlow()

    var stateValue
        get() = state.value
        protected set(value) {
            savedStateHandle[savedStateKey] = value
        }

    protected inline fun updateState(updater: (S) -> S) {
        stateValue = updater(stateValue)
    }

    protected fun Result<S, E>.handleStateResult() {
        when (val result = this@handleStateResult) {
            is Err -> mutableEvent.addEvent(result)
            is Ok -> stateValue = result.value
        }
    }
}
