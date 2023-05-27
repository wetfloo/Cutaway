package io.wetfloo.cutaway.data.repository.auth

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.auth.AuthRequest
import javax.inject.Inject

class RealAuthRepository @Inject constructor() : AuthRepository {
    override suspend fun authenticate(authRequest: AuthRequest): Result<*, Throwable> {
        TODO("Not yet implemented")
    }
}
