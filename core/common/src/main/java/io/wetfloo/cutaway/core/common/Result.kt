@file:Suppress("unused", "RedundantSuspendModifier")

package io.wetfloo.cutaway.core.common

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.runCatching
import com.github.michaelbull.result.throwIf
import kotlinx.coroutines.CancellationException

/**
 * Calls the specified function [block] and returns its encapsulated result if
 * invocation was successful, catching and encapsulating any thrown exception
 * as a failure, excepting that any [CancellationException] will be rethrown
 * in order to propagate cancellation from any parent
 * [CoroutineContext][kotlin.coroutines.CoroutineContext].
 *
 * @throws CancellationException if is thrown from the block
 */
suspend inline fun <V> runSuspendCatching(
    block: () -> V,
): Result<V, Throwable> = runCatching(block)
    .throwIf { it is CancellationException }

/**
 * Calls the specified function [block] with [this] value as its receiver and
 * returns its encapsulated result if invocation was successful, catching and
 * encapsulating any thrown exception as a failure, excepting that any
 * [CancellationException] will be rethrown in order to propagate cancellation
 * from any parent [CoroutineContext][kotlin.coroutines.CoroutineContext].
 *
 * @throws CancellationException if is thrown from the block
 */
suspend inline fun <V, T> T.runSuspendCatching(
    block: T.() -> V,
): Result<V, Throwable> = runCatching(block)
    .throwIf { it is CancellationException }

/**
 * Maps this [Result<V, E>][Result] to [Result<Unit, E>][Result],
 * removing the value
 */
fun <V, E> Result<V, E>.erase() = map {}
