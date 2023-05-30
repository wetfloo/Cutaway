package io.wetfloo.cutaway.ui.feature.createeditprofile.state

sealed interface CreateEditProfileEvent {
    object Saved : CreateEditProfileEvent
}
