package io.wetfloo.cutaway.core.common.eventflow

/**
 * Container for [Flow] with one-time events.
 * These events have multiple use cases, such as:
 * - Showing toasts and snackbars to user
 * - Navigating between destinations
 */
interface EventFlow<T> {

    /**
     * Collects given [EventFlow], giving values to [consumeBlock]
     * and automatically notifying about consumed events
     */
    suspend fun consumeAndNotify(
        filter: suspend (T) -> Boolean = { true },
        block: suspend (T) -> Unit,
    )
}
