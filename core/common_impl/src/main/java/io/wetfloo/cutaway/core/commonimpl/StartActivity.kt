package io.wetfloo.cutaway.core.commonimpl

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent

fun Context.startActivityWithIntent(
    intent: Intent,
    onActivityNotFound: () -> Unit,
    onSecurityException: () -> Unit = onActivityNotFound,
) {
    try {
        startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        onActivityNotFound()
    } catch (_: SecurityException) {
        onSecurityException()
    }
}
