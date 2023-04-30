@file:Suppress("unused")

package io.wetfloo.cutaway.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

/**
 * Returns true with a given [chance]
 */
fun booleanWithChance(chance: Double): Boolean = Random.nextFloat() < chance

/**
 * Returns true with a given [chance]
 */
fun booleanWithChance(chance: Float): Boolean = booleanWithChance(chance.toDouble())

/**
 * Returns a new [CoroutineScope] with [SupervisorJob],
 * added together with receiver's [CoroutineContext]
 */
fun CoroutineContext.supervisor(parent: Job? = null) = CoroutineScope(this + SupervisorJob(parent))
