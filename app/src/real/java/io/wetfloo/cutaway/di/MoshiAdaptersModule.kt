package io.wetfloo.cutaway.di

import com.squareup.moshi.JsonAdapter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.data.api.adapter.LocalDateTimeAdapter
import io.wetfloo.cutaway.data.api.adapter.ProfileLinkTypeAdapter
import io.wetfloo.cutaway.data.model.profile.ProfileLinkType
import java.time.LocalDateTime

@Module
@InstallIn(SingletonComponent::class)
interface MoshiAdaptersModule {
    @Binds
    fun profileLinkType(profileLinkTypeAdapter: ProfileLinkTypeAdapter): JsonAdapter<ProfileLinkType>

    @Binds
    fun localDateTime(localDataTimeAdapter: LocalDateTimeAdapter): JsonAdapter<LocalDateTime>
}
