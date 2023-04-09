package io.wetfloo.cutaway.wiring.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.core.common.CoroutineScopeProvider
import io.wetfloo.cutaway.core.common.DispatcherProvider
import io.wetfloo.cutaway.core.commonimpl.DispatcherProviderImpl
import io.wetfloo.cutaway.core.commonimpl.SupervisorCoroutineScopeProvider

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Binds
    fun bindDispatcherProvider(
        dispatcherProviderImpl: DispatcherProviderImpl,
    ): DispatcherProvider

    @Binds
    fun bindSupervisorCoroutineScopeProvider(
        supervisorCoroutineProvider: SupervisorCoroutineScopeProvider,
    ): CoroutineScopeProvider
}
