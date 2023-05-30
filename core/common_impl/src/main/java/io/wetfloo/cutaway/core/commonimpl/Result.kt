package io.wetfloo.cutaway.core.commonimpl

import android.util.Log
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onFailure

fun <V, E : Throwable> Result<V, E>.logW(tag: String) = onFailure { error ->
    Log.w(tag, error)
}
