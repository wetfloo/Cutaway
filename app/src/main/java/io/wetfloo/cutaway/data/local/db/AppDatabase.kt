package io.wetfloo.cutaway.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.wetfloo.cutaway.data.local.dao.SearchDao
import io.wetfloo.cutaway.data.model.searchuser.SearchHistoryItem

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        SearchHistoryItem::class,
    ],
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val searchDao: SearchDao
}
