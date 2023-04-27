package io.wetfloo.cutaway.core.common.eventflow

import kotlinx.coroutines.flow.Flow

/**
 * Container for [Flow] with one-time events.
 * These events have multiple use cases, such as:
 * - Showing toasts and snackbars to user
 * - Navigating between destinations
 */
interface EventFlow<T> {
    /**
     * Collects given [EventFlow], giving values to [block]
     * and automatically notifying about consumed events
     */
    suspend fun consumeAndNotify(block: suspend (T) -> Boolean)
}
