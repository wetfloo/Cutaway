package io.wetfloo.cutaway.data.repository.auth

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.core.common.booleanWithChance
import io.wetfloo.cutaway.data.AuthPreferencesManager
import io.wetfloo.cutaway.data.model.auth.AuthRequest
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class FakeAuthRepository @Inject constructor(
    private val authPreferencesManager: AuthPreferencesManager,
) : AuthRepository {
    override suspend fun authenticate(authRequest: AuthRequest): Result<*, Throwable> {
        delay(5.seconds)

        val shouldSucceed = booleanWithChance(0.9)
        return if (shouldSucceed) {
            authPreferencesManager.setToken("sometoken")
            Ok(Unit)
        } else {
            Err(Exception())
        }
    }
}
