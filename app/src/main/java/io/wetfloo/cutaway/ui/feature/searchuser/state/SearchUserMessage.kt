package io.wetfloo.cutaway.ui.feature.searchuser.state

sealed interface SearchUserMessage {
    object SearchRequested : SearchUserMessage
    object Clear : SearchUserMessage
}
