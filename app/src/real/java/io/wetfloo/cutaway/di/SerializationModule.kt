package io.wetfloo.cutaway.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.data.api.adapter.LocalDateTimeAdapter
import io.wetfloo.cutaway.data.api.adapter.ProfileLinkTypeAdapter
import io.wetfloo.cutaway.data.model.profile.ProfileLinkType
import java.time.LocalDateTime

@Module
@InstallIn(SingletonComponent::class)
class SerializationModule {
    @Provides
    fun moshi(
        localDateTimeAdapter: LocalDateTimeAdapter,
        profileLinkTypeAdapter: ProfileLinkTypeAdapter,
    ): Moshi = Moshi.Builder()
        .add(LocalDateTime::class.java, localDateTimeAdapter)
        .add(ProfileLinkType::class.java, profileLinkTypeAdapter)
        .build()
}
