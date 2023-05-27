package io.wetfloo.cutaway.data.api

import io.wetfloo.cutaway.data.model.AuthResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun authenticate(
        username: String,
        password: String,
    ): AuthResponse

    companion object {
        const val AUTH_HEADER_KEY = "Authorization"
        fun tokenToHeader(token: String) = "Bearer $token"
    }
}
