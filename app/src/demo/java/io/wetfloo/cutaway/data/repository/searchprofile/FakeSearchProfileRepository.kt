package io.wetfloo.cutaway.data.repository.searchprofile

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import io.wetfloo.cutaway.core.common.DispatcherProvider
import io.wetfloo.cutaway.core.common.runSuspendCatching
import io.wetfloo.cutaway.core.common.supervisor
import io.wetfloo.cutaway.data.local.dao.SearchDao
import io.wetfloo.cutaway.data.model.searchprofile.FoundUser
import io.wetfloo.cutaway.data.model.searchprofile.SearchHistoryItem
import io.wetfloo.cutaway.misc.utils.demo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class FakeSearchProfileRepository @Inject constructor(
    private val searchDao: SearchDao,
    private val dispatchers: DispatcherProvider,
) : SearchProfileRepository {
    private val coroutineScope = dispatchers.default.supervisor()

    override val searchHistory: StateFlow<List<SearchHistoryItem>?> = searchDao
        .observe()
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null,
        )

    override suspend fun search(query: String): Result<List<FoundUser>, Throwable> {
        updateExistingOrInsert(query)
        delay(0.seconds)
        val list = withContext(dispatchers.default) {
            (0..100).map {
                FoundUser.demo
            }
        }

        return Ok(list)
    }

    private suspend fun updateExistingOrInsert(query: String) {
        val trimmedQuery = query.trim()
        val existingHistoryItem = searchDao.find(trimmedQuery)
        with(searchDao) {
            if (existingHistoryItem != null) {
                val updatedItem = existingHistoryItem.copy(time = LocalDateTime.now())
                update(updatedItem)
            } else {
                val newItem = SearchHistoryItem(query = query)
                insert(newItem)
            }
        }

    }

    override suspend fun deleteItem(item: SearchHistoryItem) = runSuspendCatching {
        searchDao.delete(item)
    }.map { item }

    override suspend fun clearHistory() = runSuspendCatching {
        searchDao.clear()
    }
}
