package io.wetfloo.cutaway.ui.feature.auth.state


sealed interface AuthScreenMessage {
    object LoginButtonClicked : AuthScreenMessage

    object GoToRegister : AuthScreenMessage
}
