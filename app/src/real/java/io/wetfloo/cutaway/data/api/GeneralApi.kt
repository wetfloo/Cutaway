package io.wetfloo.cutaway.data.api

import io.wetfloo.cutaway.data.api.model.ImageUploadResponseDto
import io.wetfloo.cutaway.data.api.model.ProfileInformationDto
import io.wetfloo.cutaway.data.api.model.SearchPage
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface GeneralApi {
    @GET("me/profiles")
    suspend fun loadProfiles(): List<ProfileInformationDto>

    @GET("profiles/{profile_id}")
    suspend fun loadProfileInfo(
        @Path("profile_id") profileId: String,
    ): ProfileInformationDto

    @GET("profiles/search/")
    suspend fun searchProfiles(
        @Query("text_query") textQuery: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 1_000_000, // Paging 3 is a pain :^)
    ): SearchPage<ProfileInformationDto>

    @POST("me/profiles")
    suspend fun createProfile(@Body profileInformationDto: ProfileInformationDto)

    @PUT("me/profiles/{profile_id}")
    suspend fun updateProfile(
        @Body profileInformationDto: ProfileInformationDto,
        @Path("profile_id") profileId: String = profileInformationDto.id,
    )

    @DELETE("me/profiles/{profile_id}")
    suspend fun deleteProfile(@Path("profile_id") profileId: String)

    @POST("images")
    @Multipart
    suspend fun createImage(
        @Part file: MultipartBody.Part,
    ): ImageUploadResponseDto
}
