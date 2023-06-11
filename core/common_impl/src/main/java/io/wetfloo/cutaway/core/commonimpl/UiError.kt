package io.wetfloo.cutaway.core.commonimpl

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

interface UiError : Parcelable {
    fun errorString(context: Context): String
}

@Parcelize
data class Res(
    @StringRes val stringRes: Int,
    val args: List<String> = emptyList(),
) : UiError {
    constructor(
        @StringRes stringRes: Int,
        vararg args: Any,
    ) : this(
        stringRes = stringRes,
        args = args.map { it.toString() },
    )

    override fun errorString(context: Context) = context.getString(
        stringRes,
        args,
    )
}

@Parcelize
data class Raw(val string: String) : UiError {
    override fun errorString(context: Context) = string
}
