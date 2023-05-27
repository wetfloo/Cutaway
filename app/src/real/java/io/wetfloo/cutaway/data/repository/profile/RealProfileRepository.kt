package io.wetfloo.cutaway.data.repository.profile

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import io.wetfloo.cutaway.core.common.runSuspendCatching
import io.wetfloo.cutaway.data.api.GeneralApi
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RealProfileRepository @Inject constructor(
    private val api: GeneralApi,
) : ProfileRepository {
    private val _state: MutableStateFlow<ProfileInformation?> = MutableStateFlow(null)
    override val state = _state.asStateFlow()

    override suspend fun loadMyProfiles(): Result<List<ProfileInformation>, Throwable> = runSuspendCatching {
        api.loadProfiles().map(ProfileInformation::fromDto)
    }

    override suspend fun loadProfileInformation(id: String): Result<ProfileInformation, Throwable> = runSuspendCatching {
        api.loadProfileInfo(id)
    }.map(ProfileInformation::fromDto)

    companion object {
        private const val TAG = "RealProfileRepository"
    }
}
