package io.wetfloo.cutaway.data.api

import io.wetfloo.cutaway.data.api.model.ProfileInformationDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GeneralApi {
    @GET("me/profiles")
    suspend fun loadProfiles(): List<ProfileInformationDto>

    @GET("me/profiles/{id}")
    suspend fun loadProfileInfo(
        @Path("id") id: String,
    ): ProfileInformationDto
}
