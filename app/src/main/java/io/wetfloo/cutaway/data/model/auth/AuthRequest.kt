package io.wetfloo.cutaway.data.model.auth

data class AuthRequest(
    val login: String,
    val password: String,
)
