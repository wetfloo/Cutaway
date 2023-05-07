package io.wetfloo.cutaway.data.model.searchuser

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class SearchHistoryItem(
    @ColumnInfo(index = true)
    val query: String,
    val time: LocalDateTime = LocalDateTime.now(),

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
