package io.wetfloo.cutaway.ui.feature.qr

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.common.runSuspendCatching
import io.wetfloo.cutaway.core.commonimpl.StateViewModel
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.feature.qr.state.QrEvent
import io.wetfloo.cutaway.ui.feature.qr.state.QrState
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    private val barcodeScanner: GmsBarcodeScanner,
    savedStateHandle: SavedStateHandle,
) : StateViewModel<QrState, QrEvent>(
    savedStateHandle = savedStateHandle,
    savedStateKey = STATE,
    defaultStateValue = QrState.Idle,
) {
    fun scan() {
        viewModelScope.launch {
            val barcodeResult = runSuspendCatching {
                barcodeScanner
                    .startScan()
                    .await()
                    .url!!
            }
            val result = barcodeResult
                .mapError {
                    Log.w("qr", it)
                    UiError.Resource(R.string.qr_parse_failed)
                }
                .map { urlBookmark ->
                    QrEvent.UrlScanned(urlBookmark.url.toString())
                }

            mutableEvent.addEvent(result)
        }
    }


    companion object {
        private const val STATE = "QR_STATE"
    }
}
