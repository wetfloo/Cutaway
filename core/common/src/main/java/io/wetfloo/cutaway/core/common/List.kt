package io.wetfloo.cutaway.core.common

inline fun <T> Iterable<T>.forEachInBetween(
    inBetweenBlock: (T) -> Unit,
    block: (T) -> Unit,
) {
    val count = count()
    forEachIndexed { index, item ->
        block(item)

        if (index != count - 1) {
            inBetweenBlock(item)
        }
    }
}
