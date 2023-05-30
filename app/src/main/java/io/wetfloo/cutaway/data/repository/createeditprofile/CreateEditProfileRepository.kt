package io.wetfloo.cutaway.data.repository.createeditprofile

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.profile.ProfileInformation

interface CreateEditProfileRepository {
    suspend fun createProfile(profileInformation: ProfileInformation): Result<ProfileInformation, Throwable>

    suspend fun updateProfile(profileInformation: ProfileInformation): Result<ProfileInformation, Throwable>

    suspend fun deleteProfile(profileInformation: ProfileInformation): Result<Unit, Throwable>
}
