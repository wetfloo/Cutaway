package io.wetfloo.cutaway.ui.feature.qr

import android.util.Log
import androidx.core.util.PatternsCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.map
import com.github.michaelbull.result.toResultOr
import com.journeyapps.barcodescanner.ScanIntentResult
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.StateViewModel
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.feature.qr.state.QrEvent
import io.wetfloo.cutaway.ui.feature.qr.state.QrState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : StateViewModel<QrState, QrEvent, UiError>(
    savedStateHandle = savedStateHandle,
    savedStateKey = STATE,
    defaultStateValue = QrState.Idle,
) {
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
            mutableEvent.addEvent(resultEvent)
        }
    }

    companion object {
        private const val TAG = "QrViewModel"
        private const val STATE = "QR_STATE"
    }
}
