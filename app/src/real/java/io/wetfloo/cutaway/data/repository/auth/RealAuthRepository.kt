package io.wetfloo.cutaway.data.repository.auth

import com.github.michaelbull.result.map
import com.github.michaelbull.result.onSuccess
import io.wetfloo.cutaway.core.common.runSuspendCatching
import io.wetfloo.cutaway.data.api.AuthApi
import io.wetfloo.cutaway.data.model.auth.AuthRequest
import io.wetfloo.cutaway.data.preferences.AuthPreferencesManager
import javax.inject.Inject

class RealAuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val authPreferencesManager: AuthPreferencesManager,
) : AuthRepository {
    override suspend fun authenticate(authRequest: AuthRequest) = runSuspendCatching {
        authApi.authenticate(
            authRequest.login,
            authRequest.password,
        )
    }.onSuccess { authResponse ->
        // this PROBABLY shouldn't fail anyway, so it's not caught
        authPreferencesManager.setToken(authResponse.accessToken)
    }.map {}
}
