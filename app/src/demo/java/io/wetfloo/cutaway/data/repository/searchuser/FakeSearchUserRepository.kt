package io.wetfloo.cutaway.data.repository.searchuser

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.searchuser.FoundUser
import io.wetfloo.cutaway.misc.utils.demo
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class FakeSearchUserRepository @Inject constructor() : SearchUserRepository {
    override suspend fun search(query: String): Result<List<FoundUser>, Throwable> {
        delay(3.seconds)

        val list = (0..5).map {
            FoundUser.demo
        }
        return Ok(list)
    }
}
