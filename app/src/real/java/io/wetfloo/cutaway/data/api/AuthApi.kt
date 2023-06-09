package io.wetfloo.cutaway.data.api

import io.wetfloo.cutaway.data.api.model.RegisterRequestDto
import io.wetfloo.cutaway.data.model.AuthResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun authenticate(
        @Field(value = "username") username: String,
        @Field(value = "password") password: String,
    ): AuthResponse

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequestDto,
    )

    companion object {
        const val AUTH_HEADER_KEY = "Authorization"
    }
}
