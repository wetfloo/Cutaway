package io.wetfloo.cutaway.data.model.register

data class RegisterRequest(
    val email: String,
    val login: String,
    val password: String,
)
