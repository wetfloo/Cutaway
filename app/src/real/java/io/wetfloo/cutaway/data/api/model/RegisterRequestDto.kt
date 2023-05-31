package io.wetfloo.cutaway.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.wetfloo.cutaway.data.model.register.RegisterRequest

@JsonClass(generateAdapter = true)
data class RegisterRequestDto(
    @Json(name = "username") val username: String,
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
) {
    companion object {
        fun fromData(data: RegisterRequest) = RegisterRequestDto(
            email = data.email,
            username = data.login,
            password = data.password,
        )
    }
}
