package io.wetfloo.cutaway.data.repository.auth

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.auth.AuthRequest

interface AuthRepository {
    suspend fun authenticate(authRequest: AuthRequest): Result<*, Throwable>
}
