package io.wetfloo.cutaway.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class ProfileInformationDto(
    @Json(name = "banner")
    val banner: String? = null,
    @Json(name = "created_at")
    val createdAt: LocalDateTime,
    @Json(name = "education")
    val education: String? = null,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "id")
    val id: String,
    @Json(name = "lastname")
    val lastname: String? = null,
    @Json(name = "links")
    val links: List<LinkDto> = emptyList(),
    @Json(name = "name")
    val name: String,
    @Json(name = "phone_number")
    val phoneNumber: String? = null,
    @Json(name = "place_of_work")
    val placeOfWork: String? = null,
    @Json(name = "profile_picture")
    val profilePicture: String? = null,
)
