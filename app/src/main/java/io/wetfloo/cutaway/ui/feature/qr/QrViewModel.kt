package io.wetfloo.cutaway.ui.feature.qr

import android.util.Log
import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.map
import com.github.michaelbull.result.toResultOr
import com.journeyapps.barcodescanner.ScanIntentResult
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.feature.qr.state.QrEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor() : ViewModel() {
    private val _error: Channel<UiError> = Channel()
    val error = _error.receiveAsFlow()

    private val _event: Channel<QrEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun scanResult(result: ScanIntentResult?) {
        val scanContents = result?.contents
        val logMessage = if (scanContents != null) {
            "Scanned QR with data: $scanContents"
        } else {
            "Couldn't get contents out of QR"
        }
        Log.d(TAG, logMessage)

        // try to get a valid url out of QR contents.
        // if anything goes wrong, display canned error
        val scanData = scanContents?.takeIf {
            PatternsCompat.WEB_URL.matcher(it).matches()
        }
        val resultEvent = scanData.toResultOr {
            UiError.Res(R.string.qr_parse_failed)
        }.map(QrEvent::UrlScanned)
        viewModelScope.launch {
            when (resultEvent) {
                is Err -> _error.send(resultEvent.error)
                is Ok -> _event.send(resultEvent.value)
            }
        }
    }

    companion object {
        private const val TAG = "QrViewModel"
    }
}
