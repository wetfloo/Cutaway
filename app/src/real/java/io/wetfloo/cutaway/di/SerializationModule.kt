package io.wetfloo.cutaway.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.data.api.adapter.LocalDateTimeAdapter
import java.time.LocalDateTime

@Module
@InstallIn(SingletonComponent::class)
class SerializationModule {
    @Provides
    fun moshi(localDateTimeAdapter: LocalDateTimeAdapter): Moshi = Moshi.Builder()
        .add(LocalDateTime::class.java, localDateTimeAdapter)
        .build()
}
