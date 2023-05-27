package io.wetfloo.cutaway.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.data.api.interceptor.AuthTokenInterceptor
import io.wetfloo.cutaway.di.qualifier.AuthClient
import io.wetfloo.cutaway.di.qualifier.NoAuthClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OkHttpModule {
    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @NoAuthClient
    fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    @AuthClient
    fun authOkHttpClient(
        authTokenInterceptor: AuthTokenInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authTokenInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
}
