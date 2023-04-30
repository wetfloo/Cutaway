package io.wetfloo.cutaway.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.data.repository.auth.AuthRepository
import io.wetfloo.cutaway.data.repository.auth.FakeAuthRepository
import io.wetfloo.cutaway.data.repository.profile.FakeProfileRepository
import io.wetfloo.cutaway.data.repository.profile.ProfileRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
interface FakeRepositoriesModule {
    @Binds
    @Singleton
    fun profileRepository(impl: FakeProfileRepository): ProfileRepository

    @Binds
    fun authRepository(impl: FakeAuthRepository): AuthRepository
}
