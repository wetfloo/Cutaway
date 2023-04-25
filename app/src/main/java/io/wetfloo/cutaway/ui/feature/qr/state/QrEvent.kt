package io.wetfloo.cutaway.ui.feature.qr.state

sealed interface QrEvent {
    data class UrlScanned(val url: String) : QrEvent
}
