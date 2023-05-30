package io.wetfloo.cutaway.ui.feature.createeditprofile.state

import io.wetfloo.cutaway.data.model.profile.ProfileInformation

sealed interface CreateEditProfileScreenMessage {
    object Save : CreateEditProfileScreenMessage

    object GoBack : CreateEditProfileScreenMessage

    data class UpdateProfile(val profileInformation: ProfileInformation) : CreateEditProfileScreenMessage
}
