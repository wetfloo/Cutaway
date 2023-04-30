package io.wetfloo.cutaway.core.commonimpl

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

sealed class UiError : Parcelable {
    fun errorString(context: Context): String =
        when (val uiError = this@UiError) {
            is Raw -> uiError.string
            is Res -> context.getString(
                uiError.stringRes,
                uiError.args,
            )
        }

    @Parcelize
    data class Res(
        @StringRes val stringRes: Int,
        val args: List<String> = emptyList(),
    ) : UiError() {
        constructor(
            @StringRes stringRes: Int,
            vararg args: Any,
        ) : this(
            stringRes = stringRes,
            args = args.map { it.toString() },
        )
    }

    @Parcelize
    data class Raw(val string: String) : UiError()
}
