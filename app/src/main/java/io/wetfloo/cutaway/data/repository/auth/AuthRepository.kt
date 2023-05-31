package io.wetfloo.cutaway.data.repository.auth

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.auth.AuthRequest
import io.wetfloo.cutaway.data.model.register.RegisterRequest

interface AuthRepository {
    suspend fun authenticate(authRequest: AuthRequest): Result<Unit, Throwable>

    suspend fun register(registerRequest: RegisterRequest): Result<Unit, Throwable>
}
