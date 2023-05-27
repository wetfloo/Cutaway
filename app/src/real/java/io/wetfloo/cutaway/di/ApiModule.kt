package io.wetfloo.cutaway.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.data.api.API_BASE_URL
import io.wetfloo.cutaway.data.api.AuthApi
import io.wetfloo.cutaway.data.api.GeneralApi
import io.wetfloo.cutaway.di.qualifier.AuthClient
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
    fun authApi(
        @NoAuthClient client: OkHttpClient,
        moshi: Moshi,
    ): AuthApi = api(client, moshi)

    @Provides
    @Singleton
    fun generalApi(
        @AuthClient client: OkHttpClient,
        moshi: Moshi,
    ): GeneralApi = api(client, moshi)

    private inline fun <reified T> api(
        client: OkHttpClient,
        moshi: Moshi,
    ): T = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(T::class.java)
}
