package io.wetfloo.cutaway.ui.core.actions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.util.PatternsCompat
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.toResultOr
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.Res
import io.wetfloo.cutaway.core.commonimpl.UiError


fun parseScan(result: ScanIntentResult?): Result<String, UiError> {
    val scanContents = result?.contents
    val logMessage = if (scanContents != null) {
        "Scanned QR with data: $scanContents"
    } else {
        "Couldn't get contents out of QR"
    }
    Log.d("parseScan", logMessage)

    // try to get a valid url out of QR contents.
    val errorMessage = if (scanContents == null) {
        R.string.qr_no_data
    } else {
        R.string.qr_parse_failed
    }
    val scanData = scanContents?.takeIf { contents ->
        PatternsCompat.WEB_URL.matcher(contents).matches()
    }
    return scanData.toResultOr {
        Res(errorMessage)
    }
}

fun launchOnSuccess(
    context: Context,
    url: String,
) {
    Intent(
        /* action = */ Intent.ACTION_VIEW,
        /* uri = */ Uri.parse(url),
    ).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }.also(context::startActivity)
}

inline fun onQr(
    context: Context,
    onError: (UiError) -> Unit,
    result: ScanIntentResult?,
) {
    when (val parseResult = parseScan(result)) {
        is Err -> onError(parseResult.error)
        is Ok -> launchOnSuccess(
            context = context,
            url = parseResult.value,
        )
    }
}

val defaultScanOptions
    get() = ScanOptions()
        .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        .setBarcodeImageEnabled(true)
        .setOrientationLocked(false)
        .setBeepEnabled(false)
