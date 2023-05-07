package io.wetfloo.cutaway.data.model.searchuser

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistoryItem(
    val query: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
