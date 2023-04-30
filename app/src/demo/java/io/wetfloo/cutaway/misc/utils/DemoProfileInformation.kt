package io.wetfloo.cutaway.misc.utils

import io.wetfloo.cutaway.core.common.SAMPLE_PROFILE_PICTURE_URL
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.data.model.profile.ProfileInformationPiece
import io.wetfloo.cutaway.data.model.profile.ProfileInformationPieceType

val ProfileInformation.Companion.demo
    get() = ProfileInformation(
        name = "After Dark",
        status = "Reach for the sky, sinner!",
        pictureUrl = SAMPLE_PROFILE_PICTURE_URL,
        pieces = listOf(
            ProfileInformationPiece(
                value = "Here, Inc.",
                type = ProfileInformationPieceType.WORK,
            ),
            ProfileInformationPiece(
                value = "2000/01/01",
                type = ProfileInformationPieceType.BIRTHDAY,
            ),
        ),
    )
