package io.wetfloo.cutaway.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.data.api.API_BASE_URL
import io.wetfloo.cutaway.data.api.AuthApi
import io.wetfloo.cutaway.di.qualifier.NoAuthClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun authApi(@NoAuthClient client: OkHttpClient): AuthApi = api(client)

    private inline fun <reified T> api(client: OkHttpClient): T = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(T::class.java)
}
