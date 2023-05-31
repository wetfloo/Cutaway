package io.wetfloo.cutaway.data.model.profile

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import io.wetfloo.cutaway.data.api.API_BASE_URL
import io.wetfloo.cutaway.data.api.model.ProfileInformationDto
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
@Keep
@Immutable
data class ProfileInformation(
    val name: String,
    val lastName: String?,
    val profilePictureId: String?,
    val pieces: List<ProfileInformationPiece>,
    val id: String?,
    val createdAt: LocalDateTime,
) : Parcelable {
    val url
        get() = "$API_BASE_URL/profiles/$id"

    val pictureUrl: String?
        get() = profilePictureId?.pictureUrl

    val fullName
        get() = if (lastName != null) {
            "$name $lastName"
        } else name

    // needed for static extensions
    companion object {
        private val String.pictureUrl
            get() = "$API_BASE_URL/images/$this"

        val empty
            get() = ProfileInformation(
                name = "",
                lastName = null,
                profilePictureId = null,
                pieces = emptyList(),
                id = null,
                createdAt = LocalDateTime.now(),
            )

        fun fromDto(dto: ProfileInformationDto): ProfileInformation {
            fun MutableList<ProfileInformationPiece>.addFormedInfoPiece(
                value: String?,
                type: ProfileInformationPiece.Formed.Type,
            ) {
                if (value != null) {
                    val piece = ProfileInformationPiece.Formed(
                        value = value,
                        type = type,
                    )
                    add(piece)
                }
            }

            val pieces = buildList {
                addFormedInfoPiece(
                    value = dto.education,
                    type = ProfileInformationPiece.Formed.Type.EDUCATION,
                )
                addFormedInfoPiece(
                    value = dto.placeOfWork,
                    type = ProfileInformationPiece.Formed.Type.WORK,
                )
                addFormedInfoPiece(
                    value = dto.phoneNumber,
                    type = ProfileInformationPiece.Formed.Type.PHONE_NUMBER,
                )
                dto.links.mapTo(
                    destination = this,
                    transform = ProfileInformationPiece.Link::fromDto,
                )
            }

            return ProfileInformation(
                id = dto.id,
                profilePictureId = dto.profilePicture,
                name = dto.name,
                lastName = dto.lastName,
                pieces = pieces,
                createdAt = dto.createdAt,
            )
        }
    }
}
