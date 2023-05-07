package io.wetfloo.cutaway.data.model.profile

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class ProfileInformation(
    val name: String,
    val status: String?,
    val pictureUrl: String?,
    val pieces: List<ProfileInformationPiece>,
) : Parcelable {
    // needed for static extensions
    companion object
}
