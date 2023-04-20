package io.wetfloo.cutaway.core.common.eventflow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * [EventFlow] that allows to add new elements
 *
 * This distinction is useful to forbid UI from adding anything
 */
@Suppress("RedundantSuspendModifier", "MemberVisibilityCanBePrivate", "unused")
class MutableEventFlow<T>(
    initialValues: List<T> = emptyList(),
) : EventFlow<T> {

    constructor(vararg events: T) : this(listOf(*events))

    private val backingFlow = MutableStateFlow(initialValues)

    override suspend fun consumeAndNotify(
        filter: suspend (T) -> Boolean,
        block: suspend (T) -> Unit,
    ) {
        backingFlow.collect { events ->
            events
                .asReversed()
                .onEach { event ->
                    val willBeConsumed = filter(event)

                    if (willBeConsumed) {
                        removeEvent()
                        block(event)
                    }
                }
        }
    }

    /**
     * Adds new event to event queue
     */
    suspend fun addEvent(newEvent: T) {
        backingFlow.update { existingEvents ->
            existingEvents + newEvent
        }
    }

    /**
     * Removes first event when [matcher] returns `true`
     * @return `true` if element is removed successfully,
     * `false` if didn't exist
     */
    suspend fun removeEvent(matcher: suspend (T) -> Boolean): Boolean {
        var eventFound = false

        backingFlow.update { existingEvents ->
            val event = existingEvents.firstOrNull { event ->
                matcher(event)
            }

            if (event != null) {
                eventFound = true
                existingEvents - event
            } else {
                existingEvents
            }
        }

        return eventFound
    }

    /**
     * Removes oldest event in queue
     */
    suspend fun removeEvent() {
        backingFlow.update { existingEvents ->
            existingEvents.dropLast(1)
        }
    }

    /**
     * Returns `this` as basic [EventFlow] with no mutability
     */
    fun asEventFlow(): EventFlow<T> = this
}
