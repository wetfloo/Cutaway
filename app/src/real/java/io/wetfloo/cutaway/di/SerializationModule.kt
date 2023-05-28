package io.wetfloo.cutaway.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.data.model.profile.ProfileLinkType
import java.time.LocalDateTime

@Module
@InstallIn(SingletonComponent::class)
class SerializationModule {
    @Provides
    fun moshi(
        localDateTimeAdapter: JsonAdapter<LocalDateTime>,
        profileLinkTypeAdapter: JsonAdapter<ProfileLinkType>,
    ): Moshi = Moshi.Builder()
        .addAdapter(localDateTimeAdapter)
        .addAdapter(profileLinkTypeAdapter)
        .build()

    private inline fun <reified T> Moshi.Builder.addAdapter(
        adapter: JsonAdapter<T>,
    ): Moshi.Builder = add(T::class.java, adapter)
}
