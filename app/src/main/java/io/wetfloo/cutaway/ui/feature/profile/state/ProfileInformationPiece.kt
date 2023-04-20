package io.wetfloo.cutaway.ui.feature.profile.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileInformationPiece(
    val value: String,
    val type: ProfileInformationPieceType?,
) : Parcelable
