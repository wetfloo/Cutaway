package io.wetfloo.cutaway.data.repository.profile

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    val state: Flow<Result<ProfileInformation, Throwable>>

    suspend fun loadProfileInformation(): Result<ProfileInformation, Throwable>
}
