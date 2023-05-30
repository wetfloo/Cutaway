package io.wetfloo.cutaway.core.commonimpl

import android.util.Log
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onFailure

fun Result<*, Throwable>.logW(tag: String): Boolean {
    onFailure { error ->
        Log.w(tag, error)
        return true
    }

    return false
}
