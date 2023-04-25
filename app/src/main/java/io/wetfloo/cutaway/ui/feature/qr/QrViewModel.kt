package io.wetfloo.cutaway.ui.feature.qr

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.map
import com.github.michaelbull.result.toResultOr
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
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
    private val barcodeScanner: GmsBarcodeScanner,
    private val moduleInstallClient: ModuleInstallClient,
    savedStateHandle: SavedStateHandle,
) : StateViewModel<QrState, QrEvent>(
    savedStateHandle = savedStateHandle,
    savedStateKey = STATE,
    defaultStateValue = QrState.Idle,
) {
    fun scan() {
        moduleInstallClient.deferredInstall(barcodeScanner).addOnSuccessListener {
            Log.wtf(TAG, "Let's goooo")
        }
        viewModelScope.launch {
            val uiError = UiError.Resource(R.string.qr_parse_failed)
            barcodeScanner
                .startScan()
                .addOnFailureListener { exception ->
                    Log.w(TAG, exception)

                    launch {
                        mutableEvent.addEvent(Err(uiError))
                    }
                }
                .addOnSuccessListener { barcode ->
                    launch {
                        val result = barcode
                            .url
                            .toResultOr { uiError }
                            .map { it.url.toString() }
                            .map(QrEvent::UrlScanned)

                        mutableEvent.addEvent(result)
                    }
                }
        }
    }


    companion object {
        private const val TAG = "QrViewModel"
        private const val STATE = "QR_STATE"
    }
}
