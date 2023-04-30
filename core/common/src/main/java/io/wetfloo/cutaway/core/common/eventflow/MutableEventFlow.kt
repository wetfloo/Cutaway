package io.wetfloo.cutaway.core.common.eventflow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * [EventFlow] that allows to add new elements
 *
 * This distinction is useful to forbid UI from adding anything
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class MutableEventFlow<T>(
    initialValues: List<T> = emptyList(),
) : EventFlow<T> {
    constructor(vararg events: T) : this(events.toList())

    private val backingFlow = MutableStateFlow(initialValues)

    override suspend fun consumeMatching(block: suspend (T) -> Boolean) {
        backingFlow.collect { events ->
            events
                .asReversed()
                .onEach { event ->
                    val isConsideredConsumed = block(event)

                    if (isConsideredConsumed) {
                        removeEvent()
                    }
                }
        }
    }

    /**
     * Adds new event to event queue
     */
    fun addEvent(newEvent: T) {
        backingFlow.update { existingEvents ->
            existingEvents + newEvent
        }
    }

    /**
     * Removes first event when [matcher] returns `true`
     * @return `true` if element is removed successfully,
     * `false` if didn't exist
     */
    fun removeEvent(matcher: (T) -> Boolean): Boolean {
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
    fun removeEvent() {
        backingFlow.update { existingEvents ->
            existingEvents.dropLast(1)
        }
    }

    /**
     * Returns `this` as basic [EventFlow] with no mutability
     */
    fun asEventFlow(): EventFlow<T> = this
}
