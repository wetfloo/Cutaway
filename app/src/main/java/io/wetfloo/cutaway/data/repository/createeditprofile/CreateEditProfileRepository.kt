package io.wetfloo.cutaway.data.repository.createeditprofile

import android.net.Uri
import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.profile.ProfileInformation

interface CreateEditProfileRepository {
    suspend fun createProfile(profileInformation: ProfileInformation): Result<ProfileInformation, Throwable>

    suspend fun updateProfile(profileInformation: ProfileInformation): Result<ProfileInformation, Throwable>

    suspend fun deleteProfile(profileId: String): Result<Unit, Throwable>

    suspend fun uploadImage(uri: Uri): Result<String, Throwable>
}
