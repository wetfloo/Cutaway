package io.wetfloo.cutaway.data.model.profile

import android.os.Parcelable
import androidx.annotation.Keep
import io.wetfloo.cutaway.data.api.model.ProfileInformationDto
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class ProfileInformation(
    val name: String,
    val pictureUrl: String?,
    val pieces: List<ProfileInformationPiece>,
    val id: String,
) : Parcelable {
    // needed for static extensions
    companion object {
        fun fromDto(dto: ProfileInformationDto): ProfileInformation {
            fun MutableList<ProfileInformationPiece>.addInfoPiece(
                value: String?,
                type: ProfileInformationPieceType,
            ) {
                if (value != null) {
                    val piece = ProfileInformationPiece(
                        value = value,
                        type = type,
                    )
                    add(piece)
                }
            }

            val name = if (dto.lastname != null) {
                dto.name + dto.lastname
            } else dto.name

            val pieces = buildList {
                addInfoPiece(
                    value = dto.education,
                    type = ProfileInformationPieceType.EDUCATION,
                )
                addInfoPiece(
                    value = dto.placeOfWork,
                    type = ProfileInformationPieceType.WORK,
                )
            }

            return ProfileInformation(
                id = dto.id,
                pictureUrl = dto.profilePicture,
                name = name,
                pieces = pieces,
            )
        }
    }
}
