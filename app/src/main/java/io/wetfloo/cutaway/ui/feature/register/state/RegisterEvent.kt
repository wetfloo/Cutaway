package io.wetfloo.cutaway.ui.feature.register.state

sealed interface RegisterEvent {
    object Success : RegisterEvent
}
