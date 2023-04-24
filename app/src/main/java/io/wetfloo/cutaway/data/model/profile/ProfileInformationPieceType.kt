package io.wetfloo.cutaway.data.model.profile

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CastForEducation
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector
import io.wetfloo.cutaway.R

enum class ProfileInformationPieceType(
    @StringRes val headerStringRes: Int,
    val icon: ImageVector,
    val iconDescription: String? = null,
) {
    EDUCATION(
        headerStringRes = R.string.piece_education,
        icon = Icons.Default.CastForEducation,
    ),
    WORK(
        headerStringRes = R.string.piece_work,
        icon = Icons.Default.Work,
    ),
    LOCATION(
        headerStringRes = R.string.piece_location,
        icon = Icons.Default.LocationOn,
    ),
    BIRTHDAY(
        headerStringRes = R.string.piece_birthday,
        icon = Icons.Default.Cake,
    ),
    INTERESTS(
        headerStringRes = R.string.piece_interests,
        icon = Icons.Default.Star,
    ),
}
