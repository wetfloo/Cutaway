package io.wetfloo.cutaway.data.api.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import io.wetfloo.cutaway.data.model.profile.ProfileLinkType
import javax.inject.Inject

class ProfileLinkTypeAdapter @Inject constructor() : JsonAdapter<ProfileLinkType>() {
    override fun fromJson(reader: JsonReader): ProfileLinkType? {
        val linkTypeName = reader
            .nextString()
            .trim()
            .uppercase()
        return if (reader.peek() != JsonReader.Token.NULL) {
            ProfileLinkType.valueOf(linkTypeName)
        } else {
            reader.nextNull()
        }
    }

    override fun toJson(writer: JsonWriter, value: ProfileLinkType?) {
        writer.value(value?.name?.lowercase())
    }
}
