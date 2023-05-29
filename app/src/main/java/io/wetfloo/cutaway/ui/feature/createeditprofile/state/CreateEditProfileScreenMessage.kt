package io.wetfloo.cutaway.ui.feature.createeditprofile.state

sealed interface CreateEditProfileScreenMessage {
    object Save : CreateEditProfileScreenMessage

    object GoBack : CreateEditProfileScreenMessage
}
