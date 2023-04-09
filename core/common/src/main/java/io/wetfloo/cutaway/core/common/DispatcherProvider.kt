package io.wetfloo.cutaway.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {

    val main: CoroutineDispatcher
        get() = Dispatchers.Main

    val io: CoroutineDispatcher
        get() = Dispatchers.IO

    val default: CoroutineDispatcher
        get() = Dispatchers.Default

    val immediate: CoroutineDispatcher
        get() = Dispatchers.Main.immediate

    val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}
