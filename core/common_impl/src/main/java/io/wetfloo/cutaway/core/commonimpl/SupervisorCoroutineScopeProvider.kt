package io.wetfloo.cutaway.core.commonimpl

import io.wetfloo.cutaway.core.common.CoroutineScopeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SupervisorCoroutineScopeProvider @Inject constructor() : CoroutineScopeProvider {
    override infix fun scopeFrom(
        coroutineContext: CoroutineContext,
    ): CoroutineScope = CoroutineScope(coroutineContext + SupervisorJob())
}
