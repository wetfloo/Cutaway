package io.wetfloo.cutaway.ui.feature.auth

sealed interface AuthEvent {
    object Success : AuthEvent
}
