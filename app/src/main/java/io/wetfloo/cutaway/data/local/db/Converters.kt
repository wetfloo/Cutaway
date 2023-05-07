package io.wetfloo.cutaway.data.local.db

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun fromLocalDateTime(value: String?) = value?.let(LocalDateTime::parse)

    @TypeConverter
    fun toLocalDateTime(date: LocalDateTime?) = date?.toString()
}
