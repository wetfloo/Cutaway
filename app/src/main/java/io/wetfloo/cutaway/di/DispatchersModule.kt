package io.wetfloo.cutaway.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.core.common.DispatcherProvider
import io.wetfloo.cutaway.core.commonimpl.DispatcherProviderImpl

@Module
@InstallIn(SingletonComponent::class)
interface DispatchersModule {
    @Binds
    fun dispatcherProvider(impl: DispatcherProviderImpl): DispatcherProvider
}
