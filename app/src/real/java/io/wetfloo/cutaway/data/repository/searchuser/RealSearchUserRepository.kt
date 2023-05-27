package io.wetfloo.cutaway.data.repository.searchuser

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.searchuser.FoundUser
import io.wetfloo.cutaway.data.model.searchuser.SearchHistoryItem
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RealSearchUserRepository @Inject constructor() : SearchUserRepository {
    override val searchHistory: StateFlow<List<SearchHistoryItem>?>
        get() = TODO("Not yet implemented")

    override suspend fun search(query: String): Result<List<FoundUser>, Throwable> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(item: SearchHistoryItem): Result<SearchHistoryItem, Throwable> {
        TODO("Not yet implemented")
    }

    override suspend fun clearHistory(): Result<*, Throwable> {
        TODO("Not yet implemented")
    }
}
