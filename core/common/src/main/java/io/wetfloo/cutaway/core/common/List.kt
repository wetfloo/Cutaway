package io.wetfloo.cutaway.core.common

inline fun <T> Iterable<T>.forEachInBetween(
    inBetweenBlock: (T) -> Unit,
    block: (T) -> Unit,
) {
    val lastIndex = count() - 1
    forEachIndexed { index, item ->
        block(item)

        if (index != lastIndex) {
            inBetweenBlock(item)
        }
    }
}

inline fun <T> Iterable<T>.forEachInBetweenIndexed(
    inBetweenBlock: (Int, T) -> Unit,
    block: (Int, T) -> Unit,
) {
    val lastIndex = count() - 1
    forEachIndexed { index, item ->
        block(index, item)

        if (index != lastIndex) {
            inBetweenBlock(index, item)
        }
    }
}
