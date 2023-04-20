package io.wetfloo.cutaway.ui.feature.profile

sealed interface ProfileScreenMessage {
    object EditProfile : ProfileScreenMessage
    object ShowQrCode : ProfileScreenMessage
}
