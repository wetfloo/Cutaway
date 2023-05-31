package io.wetfloo.cutaway.data

import io.wetfloo.cutaway.core.common.DispatcherProvider
import io.wetfloo.cutaway.core.common.supervisor
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutStateEventBus @Inject constructor(
    private val loginStorageResetter: LoginStateResetter,
    dispatchers: DispatcherProvider,
) {
    private val scope = dispatchers.default.supervisor()

    // don't keep more than one logout event around
    private val _events: Channel<Unit> = Channel(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )
    val events = _events.receiveAsFlow()

    private var logoutJob: Job? = null

    fun sendLogoutMessage() {
        if (logoutJob?.isActive == true) return
        /*

            There is a problem with how state is handled is this app.
            Basically, if any ViewModel that's scoped to MainActivity holds
            any state, it's gonna be really problematic to logout the user
            and reset all the state correctly.

            And that's why you don't store state in your ViewModels and then
            scope them to Activities for no reason.

         */
        logoutJob = scope.launch {
            loginStorageResetter.reset()
            _events.send(Unit)
        }
    }
}
