package io.wetfloo.cutaway.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.wetfloo.cutaway.data.model.searchuser.SearchHistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query(
        """
        SELECT * 
        FROM SearchHistoryItem
        """
    )
    fun observe(): Flow<List<SearchHistoryItem>>

    @Query(
        """
            SELECT * 
            FROM SearchHistoryItem
            WHERE `query` = :query
            ORDER BY id DESC
            LIMIT 1
        """
    )
    suspend fun find(query: String): SearchHistoryItem?

    @Insert
    suspend fun insert(item: SearchHistoryItem)

    @Update
    suspend fun update(item: SearchHistoryItem)


    @Query(
        """
            DELETE 
            FROM SearchHistoryItem
        """
    )
    suspend fun clear()
}
