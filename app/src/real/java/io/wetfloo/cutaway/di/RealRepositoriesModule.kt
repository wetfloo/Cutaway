package io.wetfloo.cutaway.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.data.repository.auth.AuthRepository
import io.wetfloo.cutaway.data.repository.auth.RealAuthRepository
import io.wetfloo.cutaway.data.repository.profile.ProfileRepository
import io.wetfloo.cutaway.data.repository.profile.RealProfileRepository
import io.wetfloo.cutaway.data.repository.searchuser.RealSearchUserRepository
import io.wetfloo.cutaway.data.repository.searchuser.SearchUserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
interface RealRepositoriesModule {
    @Binds
    @Singleton
    fun profileRepository(impl: RealProfileRepository): ProfileRepository

    @Binds
    fun authRepository(impl: RealAuthRepository): AuthRepository

    @Binds
    @Singleton
    fun searchUserRepository(impl: RealSearchUserRepository): SearchUserRepository
}
