package io.wetfloo.cutaway.data.repository.searchuser

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.searchuser.FoundUser

interface SearchUserRepository {
    suspend fun search(query: String): Result<List<FoundUser>, Throwable>
}
