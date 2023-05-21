package io.wetfloo.cutaway.core.common

inline fun <T> lazyUnsafe(crossinline initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE) {
    initializer()
}
