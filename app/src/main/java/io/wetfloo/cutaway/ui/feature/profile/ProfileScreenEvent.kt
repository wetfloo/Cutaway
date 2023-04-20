package io.wetfloo.cutaway.ui.feature.profile

sealed interface ProfileScreenEvent {
    object EditProfile : ProfileScreenEvent
    object ShowQrCode : ProfileScreenEvent
}
