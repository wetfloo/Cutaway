package io.wetfloo.cutaway.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.wetfloo.cutaway.data.model.searchuser.SearchHistoryItem

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        SearchHistoryItem::class,
    ],
)
abstract class AppDatabase : RoomDatabase()
