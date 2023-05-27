package io.wetfloo.cutaway.data.api.interceptor

import io.wetfloo.cutaway.data.api.AuthApi
import io.wetfloo.cutaway.data.preferences.AuthPreferencesStorage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor(
    private val authPreferencesStorage: AuthPreferencesStorage,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            authPreferencesStorage.preferencesFlow.first().token
        }
        val requestWithToken = accessToken?.let { token ->
            chain.request()
                .newBuilder()
                .addHeader(AuthApi.AUTH_HEADER_KEY, AuthApi.tokenToHeader(token))
                .build()
        }
        return chain.proceed(requestWithToken ?: chain.request())
    }
}
