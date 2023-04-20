package io.wetfloo.cutaway.ui.feature.auth

sealed interface AuthScreenMessage {
    object LoginButtonClicked : AuthScreenMessage
}
