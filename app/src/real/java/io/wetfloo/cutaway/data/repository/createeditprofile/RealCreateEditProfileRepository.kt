package io.wetfloo.cutaway.data.repository.createeditprofile

import com.github.michaelbull.result.map
import io.wetfloo.cutaway.core.common.runSuspendCatching
import io.wetfloo.cutaway.data.api.GeneralApi
import io.wetfloo.cutaway.data.api.model.ProfileInformationDto
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import javax.inject.Inject

class RealCreateEditProfileRepository @Inject constructor(
    private val api: GeneralApi,
) : CreateEditProfileRepository {
    override suspend fun createProfile(profileInformation: ProfileInformation) = runSuspendCatching {
        api.createProfile(ProfileInformationDto.fromData(profileInformation))
    }.map { profileInformation }

    override suspend fun updateProfile(profileInformation: ProfileInformation) = runSuspendCatching {
        api.updateProfile(ProfileInformationDto.fromData(profileInformation))
    }.map { profileInformation }

    override suspend fun deleteProfile(profileId: String) = runSuspendCatching {
        api.deleteProfile(profileId)
    }
}
