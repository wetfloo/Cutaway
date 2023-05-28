package io.wetfloo.cutaway.data.repository.searchprofile

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.searchprofile.FoundUser
import io.wetfloo.cutaway.data.model.searchprofile.SearchHistoryItem
import kotlinx.coroutines.flow.StateFlow

interface SearchProfileRepository {
    val searchHistory: StateFlow<List<SearchHistoryItem>?>

    suspend fun search(query: String): Result<List<FoundUser>, Throwable>
    suspend fun deleteItem(item: SearchHistoryItem): Result<SearchHistoryItem, Throwable>
    suspend fun clearHistory(): Result<Unit, Throwable>
}
