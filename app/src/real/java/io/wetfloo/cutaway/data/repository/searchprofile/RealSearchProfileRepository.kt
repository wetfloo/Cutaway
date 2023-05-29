package io.wetfloo.cutaway.data.repository.searchprofile

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import io.wetfloo.cutaway.core.common.DispatcherProvider
import io.wetfloo.cutaway.core.common.erase
import io.wetfloo.cutaway.core.common.runSuspendCatching
import io.wetfloo.cutaway.core.common.supervisor
import io.wetfloo.cutaway.data.api.GeneralApi
import io.wetfloo.cutaway.data.local.dao.SearchDao
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.data.model.searchprofile.SearchHistoryItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime
import javax.inject.Inject

class RealSearchProfileRepository @Inject constructor(
    private val searchDao: SearchDao,
    private val api: GeneralApi,
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

    override suspend fun search(query: String): Result<List<ProfileInformation>, Throwable> {
        updateExistingOrInsert(query)
        return runSuspendCatching {
            api.searchProfiles(query.trim())
        }.map { page ->
            page.items.map(ProfileInformation::fromDto)
        }
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
    }.erase()
}
