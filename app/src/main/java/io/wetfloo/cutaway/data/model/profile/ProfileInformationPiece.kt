package io.wetfloo.cutaway.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileInformationPiece(
    val value: String,
    val type: ProfileInformationPieceType?,
) : Parcelable
