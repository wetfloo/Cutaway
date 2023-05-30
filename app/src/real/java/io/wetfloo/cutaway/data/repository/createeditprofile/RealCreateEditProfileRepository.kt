package io.wetfloo.cutaway.data.repository.createeditprofile

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.api.GeneralApi
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import javax.inject.Inject

class RealCreateEditProfileRepository @Inject constructor(
    private val api: GeneralApi,
) : CreateEditProfileRepository {
    override suspend fun createProfile(profileInformation: ProfileInformation): Result<ProfileInformation, Throwable> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfile(profileInformation: ProfileInformation): Result<ProfileInformation, Throwable> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProfile(profileInformation: ProfileInformation): Result<Unit, Throwable> {
        TODO("Not yet implemented")
    }

}
