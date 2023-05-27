package io.wetfloo.cutaway.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.data.api.interceptor.AuthTokenInterceptor
import io.wetfloo.cutaway.di.qualifier.AuthClient
import io.wetfloo.cutaway.di.qualifier.NoAuthClient
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OkHttpModule {
    @Provides
    @Singleton
    @NoAuthClient
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    @AuthClient
    fun authOkHttpClient(
        authTokenInterceptor: AuthTokenInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authTokenInterceptor)
        .build()
}
