package io.wetfloo.cutaway.data.repository.profile

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    val state: StateFlow<List<ProfileInformation>?>

    suspend fun loadMyProfiles(): Result<List<ProfileInformation>, Throwable>
    suspend fun loadProfileInformation(id: String): Result<ProfileInformation, Throwable>
    suspend fun deleteProfile(profileInformation: ProfileInformation): Result<Unit, Throwable>
}
