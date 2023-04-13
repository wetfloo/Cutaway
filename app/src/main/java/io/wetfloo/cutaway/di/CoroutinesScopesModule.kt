package io.wetfloo.cutaway.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.core.common.CoroutineScopeProvider
import io.wetfloo.cutaway.core.commonimpl.SupervisorCoroutineScopeProvider

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesScopesModule {
    @Binds
    fun coroutineScopesProvider(impl: SupervisorCoroutineScopeProvider): CoroutineScopeProvider
}
