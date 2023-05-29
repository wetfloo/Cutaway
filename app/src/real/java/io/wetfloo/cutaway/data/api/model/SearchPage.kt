package io.wetfloo.cutaway.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchPage<T>(
    @Json(name = "total") val total: Int,
    @Json(name = "items") val items: List<T>,
)
