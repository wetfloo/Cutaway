package io.wetfloo.cutaway.core.commonimpl

import android.content.Context
import androidx.annotation.StringRes

sealed interface UiError {
    fun errorString(context: Context): String =
        when (val uiError = this@UiError) {
            is Raw -> uiError.string
            is Resource -> context.getString(
                uiError.stringRes,
                uiError.args,
            )
        }

    data class Resource(
        @StringRes val stringRes: Int,
        val args: Array<Any> = emptyArray(),
    ) : UiError {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Resource

            if (stringRes != other.stringRes) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = stringRes
            result = 31 * result + args.contentHashCode()
            return result
        }
    }

    data class Raw(val string: String) : UiError
}
