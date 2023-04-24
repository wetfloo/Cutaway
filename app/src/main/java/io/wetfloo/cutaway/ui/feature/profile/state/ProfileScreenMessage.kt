package io.wetfloo.cutaway.ui.feature.profile.state

sealed interface ProfileScreenMessage {
    object EditProfile : ProfileScreenMessage
    object ShowQrCode : ProfileScreenMessage
    object ShowProfileDetailedInformation : ProfileScreenMessage
}
