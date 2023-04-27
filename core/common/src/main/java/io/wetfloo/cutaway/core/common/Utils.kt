@file:Suppress("unused")

package io.wetfloo.cutaway.core.common

import kotlin.random.Random

/**
 * Returns true with a given [chance]
 */
fun booleanWithChance(chance: Double): Boolean = Random.nextFloat() < chance

/**
 * Returns true with a given [chance]
 */
fun booleanWithChance(chance: Float): Boolean = booleanWithChance(chance.toDouble())
