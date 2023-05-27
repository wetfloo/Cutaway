package io.wetfloo.cutaway.data.repository.profile

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RealProfileRepository @Inject constructor() : ProfileRepository {
    override val state: StateFlow<ProfileInformation?>
        get() = TODO("Not yet implemented")

    override suspend fun loadProfileInformation(id: String?): Result<ProfileInformation, Throwable> {
        TODO("Not yet implemented")
    }
}
