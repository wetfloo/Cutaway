package io.wetfloo.cutaway.core.common.eventflow

/**
 * Collects given [EventFlow], giving values to [block]
 * and automatically removes consumed events
 */
suspend inline fun <T> EventFlow<T>.consume(crossinline block: suspend (T) -> Unit) {
    consumeMatching {
        block(it)
        true
    }
}
