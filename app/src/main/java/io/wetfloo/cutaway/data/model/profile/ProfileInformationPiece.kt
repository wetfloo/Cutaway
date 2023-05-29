package io.wetfloo.cutaway.data.model.profile

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.data.api.model.LinkDto
import kotlinx.parcelize.Parcelize

//@Parcelize
//data class ProfileInformationPiece(
//    val value: String,
//    val type: ProfileInformationPieceFormed?,
//) : Parcelable

@Immutable
sealed interface ProfileInformationPiece : Parcelable {
    @Parcelize
    @Immutable
    data class Formed(
        val value: String,
        val type: Type,
    ) : ProfileInformationPiece {
        enum class Type(
            @StringRes val headerStringRes: Int,
            val icon: ImageVector,
            val iconDescription: String? = null,
        ) {
            EDUCATION(
                headerStringRes = R.string.piece_education,
                icon = Icons.Default.HistoryEdu,
            ),
            WORK(
                headerStringRes = R.string.piece_work,
                icon = Icons.Default.Work,
            ),
            PHONE_NUMBER(
                headerStringRes = R.string.piece_phone_number,
                icon = Icons.Default.Phone,
            ),
        }
    }

    @Parcelize
    @Immutable
    data class Link(
        val url: String,
        val title: String,
        val linkType: ProfileLinkType,
    ) : ProfileInformationPiece {
        companion object {
            fun fromDto(link: LinkDto): Link {
                return with(link) {
                    Link(
                        url = url,
                        title = title,
                        linkType = linkType,
                    )
                }
            }
        }
    }
}
