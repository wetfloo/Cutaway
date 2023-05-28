package io.wetfloo.cutaway.data.repository.searchprofile

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.searchprofile.FoundUser
import io.wetfloo.cutaway.data.model.searchprofile.SearchHistoryItem
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RealSearchProfileRepository @Inject constructor() : SearchProfileRepository {
    override val searchHistory: StateFlow<List<SearchHistoryItem>?>
        get() = TODO("Not yet implemented")

    override suspend fun search(query: String): Result<List<FoundUser>, Throwable> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(item: SearchHistoryItem): Result<SearchHistoryItem, Throwable> {
        TODO("Not yet implemented")
    }

    override suspend fun clearHistory(): Result<Unit, Throwable> {
        TODO("Not yet implemented")
    }
}
