package io.wetfloo.cutaway.ui.feature.profiledetails.state

import io.wetfloo.cutaway.data.model.profile.ProfileInformation

sealed interface ProfileDetailedScreenMessage {
    object GoBack : ProfileDetailedScreenMessage

    data class ShowQrCode(
        val profile: ProfileInformation,
    ) : ProfileDetailedScreenMessage
}
