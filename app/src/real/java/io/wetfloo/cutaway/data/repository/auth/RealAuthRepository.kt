package io.wetfloo.cutaway.data.repository.auth

import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.onSuccess
import io.wetfloo.cutaway.core.common.erase
import io.wetfloo.cutaway.core.common.runSuspendCatching
import io.wetfloo.cutaway.data.api.AuthApi
import io.wetfloo.cutaway.data.api.model.RegisterRequestDto
import io.wetfloo.cutaway.data.model.auth.AuthRequest
import io.wetfloo.cutaway.data.model.register.RegisterRequest
import io.wetfloo.cutaway.data.preferences.AuthPreferencesStorage
import javax.inject.Inject

class RealAuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val authPreferencesStorage: AuthPreferencesStorage,
) : AuthRepository {
    override suspend fun authenticate(authRequest: AuthRequest) = runSuspendCatching {
        authApi.authenticate(
            authRequest.login,
            authRequest.password,
        )
    }.onSuccess { authResponse ->
        // this PROBABLY shouldn't fail anyway, so it's not caught
        authPreferencesStorage.setToken(authResponse.accessToken)
    }.erase()

    override suspend fun register(registerRequest: RegisterRequest) = runSuspendCatching {
        authApi.register(RegisterRequestDto.fromData(registerRequest))
    }.andThen {
        val authRequest = AuthRequest(
            login = registerRequest.login,
            password = registerRequest.password,
        )

        authenticate(authRequest)
    }
}
