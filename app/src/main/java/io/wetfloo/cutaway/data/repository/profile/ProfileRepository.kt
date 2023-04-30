package io.wetfloo.cutaway.data.repository.profile

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    val state: StateFlow<ProfileInformation?>

    suspend fun loadProfileInformation(): Result<ProfileInformation, Throwable>
}
