package io.wetfloo.cutaway.ui.feature.auth.state


sealed interface AuthEvent {
    object Success : AuthEvent
}
