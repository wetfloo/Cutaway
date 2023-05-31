package io.wetfloo.cutaway.ui.feature.register.state

sealed interface RegisterScreenMessage {
    object RegisterButtonClicked : RegisterScreenMessage

    object GoToAuth : RegisterScreenMessage
}
