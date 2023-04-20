package io.wetfloo.cutaway.ui.feature.auth

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class AuthState(
    val isLoading: Boolean = false,
) : Parcelable
