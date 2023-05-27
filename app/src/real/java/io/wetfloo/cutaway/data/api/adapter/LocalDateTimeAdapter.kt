package io.wetfloo.cutaway.data.api.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LocalDateTimeAdapter @Inject constructor() : JsonAdapter<LocalDateTime>() {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

    @FromJson
    override fun fromJson(reader: JsonReader): LocalDateTime? {
        val dateString = reader.nextString()
        return LocalDateTime.parse(dateString, formatter)
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        if (value != null) {
            val dateString = formatter.format(value)
            writer.value(dateString)
        }
    }

}
