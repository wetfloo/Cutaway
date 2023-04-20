package io.wetfloo.cutaway.ui.feature.auth

import androidx.compose.runtime.Immutable

@Immutable
data class AuthState(
    val isLoading: Boolean = false,
)
