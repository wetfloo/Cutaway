package io.wetfloo.cutaway.ui.core

import androidx.compose.runtime.Composable
import io.wetfloo.cutaway.core.common.forEachInBetween

@Composable
fun <T> InBetween(
    items: Iterable<T>,
    inBetweenBlock: @Composable (T) -> Unit,
    content: @Composable (T) -> Unit,
) {
    items.forEachInBetween(
        inBetweenBlock = {
            inBetweenBlock(it)
        },
    ) {
        content(it)
    }
}
