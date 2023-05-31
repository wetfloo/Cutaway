package io.wetfloo.cutaway.ui.feature.profile.state

import io.wetfloo.cutaway.data.model.profile.ProfileInformation

sealed interface ProfileScreenMessage {
    data class EditProfile(
        val profile: ProfileInformation,
    ) : ProfileScreenMessage

    object CreateProfile : ProfileScreenMessage

    data class DeleteProfile(
        val profile: ProfileInformation,
    ) : ProfileScreenMessage

    data class ShowQrCode(
        val profile: ProfileInformation,
    ) : ProfileScreenMessage

    data class ShowProfileDetailedInformation(
        val profile: ProfileInformation,
    ) : ProfileScreenMessage

    object Logout : ProfileScreenMessage
}
