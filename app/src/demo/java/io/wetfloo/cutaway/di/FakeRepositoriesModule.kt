package io.wetfloo.cutaway.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wetfloo.cutaway.data.repository.profile.FakeProfileRepository
import io.wetfloo.cutaway.data.repository.profile.ProfileRepository

@Module
@InstallIn(SingletonComponent::class)
interface FakeRepositoriesModule {
    @Binds
    fun profileRepository(impl: FakeProfileRepository): ProfileRepository
}
