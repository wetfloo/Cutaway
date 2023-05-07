package io.wetfloo.cutaway.data.model.searchuser

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class SearchHistoryItem(
    val query: String,
    val time: LocalDateTime,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
