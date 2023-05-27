package io.wetfloo.cutaway.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LinkDto(
    @Json(name = "link_type")
    val linkType: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
)
