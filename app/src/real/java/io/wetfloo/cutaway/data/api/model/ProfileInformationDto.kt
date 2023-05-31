package io.wetfloo.cutaway.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.data.model.profile.ProfileInformationPiece
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class ProfileInformationDto(
    @Json(name = "banner") val banner: String? = null,
    @Json(name = "created_at") val createdAt: LocalDateTime,
    @Json(name = "education") val education: String? = null,
    @Json(name = "email") val email: String? = null,
    @Json(name = "id") val id: String,
    @Json(name = "lastname") val lastName: String? = null,
    @Json(name = "links") val links: List<LinkDto> = emptyList(),
    @Json(name = "name") val name: String,
    @Json(name = "phone_number") val phoneNumber: String? = null,
    @Json(name = "place_of_work") val placeOfWork: String? = null,
    @Json(name = "profile_picture") val profilePicture: String? = null,
) {
    companion object {
        fun fromData(profileInformation: ProfileInformation): ProfileInformationDto {
            val pieces = profileInformation.pieces
            return ProfileInformationDto(
                name = profileInformation.name,
                lastName = profileInformation.lastName,
                createdAt = profileInformation.createdAt,
                id = profileInformation.id ?: "",
                education = pieces
                    .findFormedPieceValue(ProfileInformationPiece.Formed.Type.EDUCATION),
                placeOfWork = pieces
                    .findFormedPieceValue(ProfileInformationPiece.Formed.Type.WORK),
                phoneNumber = pieces
                    .findFormedPieceValue(ProfileInformationPiece.Formed.Type.PHONE_NUMBER),
                profilePicture = profileInformation.profilePictureId,
                links = pieces
                    .filterIsInstance<ProfileInformationPiece.Link>()
                    .map(LinkDto::fromData),
            )
        }

        private fun List<ProfileInformationPiece>.findFormedPieceValue(
            type: ProfileInformationPiece.Formed.Type,
        ): String? = filterIsInstance<ProfileInformationPiece.Formed>().firstOrNull { piece ->
            piece.type == type
        }?.value
    }
}
