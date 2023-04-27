package io.wetfloo.cutaway.core.common

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * Provides a coroutine scope outside of places
 * where coroutine scopes are typically available
 */
interface CoroutineScopeProvider {
    /**
     * @param coroutineContext Context to be applied to a new coroutine
     * @return A new [CoroutineScope]
     */
    infix fun scopeFrom(
        coroutineContext: CoroutineContext,
    ): CoroutineScope = CoroutineScope(coroutineContext)
}
