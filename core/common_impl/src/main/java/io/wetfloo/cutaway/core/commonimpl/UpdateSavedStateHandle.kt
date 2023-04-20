package io.wetfloo.cutaway.core.commonimpl

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle

inline fun <T : Parcelable> SavedStateHandle.update(
    key: String,
    defaultValue: T,
    updater: (T) -> T?,
) {
    val oldValue: T = this[key] ?: defaultValue
    val newValue = updater(oldValue)
    this[key] = newValue
}
