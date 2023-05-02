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
 * Generates random alphanumeric [String] of given [length]
 */
fun randomAlphaNumericString(length: Int): String = (0..length).map {
    val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val charIndex = Random.nextInt(0, charPool.count())
    return@map charPool[charIndex]
}.joinToString("")

/**
 * Returns a new [CoroutineScope] with [SupervisorJob],
 * added together with receiver's [CoroutineContext]
 */
fun CoroutineContext.supervisor(parent: Job? = null) = CoroutineScope(this + SupervisorJob(parent))
