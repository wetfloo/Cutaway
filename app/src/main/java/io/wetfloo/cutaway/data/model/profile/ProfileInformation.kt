package io.wetfloo.cutaway.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileInformation(
    val name: String,
    val status: String?,
    val pictureUrl: String?,
    val pieces: List<ProfileInformationPiece>,
) : Parcelable
