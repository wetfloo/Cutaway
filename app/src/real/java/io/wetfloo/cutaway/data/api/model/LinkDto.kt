package io.wetfloo.cutaway.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.wetfloo.cutaway.data.model.profile.ProfileInformationPiece
import io.wetfloo.cutaway.data.model.profile.ProfileLinkType

@JsonClass(generateAdapter = true)
data class LinkDto(
    @Json(name = "link_type") val linkType: ProfileLinkType,
    @Json(name = "title") val title: String,
    @Json(name = "url") val url: String,
) {
    companion object {
        fun fromData(link: ProfileInformationPiece.Link) = LinkDto(
            linkType = link.linkType,
            title = link.title,
            url = link.url,
        )
    }
}
