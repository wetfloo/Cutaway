package io.wetfloo.cutaway.data.repository.searchuser

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.searchuser.FoundUser
import io.wetfloo.cutaway.data.model.searchuser.SearchHistoryItem
import kotlinx.coroutines.flow.StateFlow

interface SearchUserRepository {
    val searchHistory: StateFlow<List<SearchHistoryItem>?>

    suspend fun search(query: String): Result<List<FoundUser>, Throwable>
    suspend fun deleteItem(item: SearchHistoryItem): Result<SearchHistoryItem, Throwable>
}
