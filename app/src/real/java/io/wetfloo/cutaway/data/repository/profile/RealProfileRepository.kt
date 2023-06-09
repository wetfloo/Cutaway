package io.wetfloo.cutaway.data.repository.profile

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.recoverIf
import io.wetfloo.cutaway.core.common.runSuspendCatching
import io.wetfloo.cutaway.core.commonimpl.logW
import io.wetfloo.cutaway.data.api.GeneralApi
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.internal.http.HTTP_NOT_FOUND
import retrofit2.HttpException
import javax.inject.Inject

class RealProfileRepository @Inject constructor(
    private val api: GeneralApi,
) : ProfileRepository {
    private val _state: MutableStateFlow<List<ProfileInformation>?> = MutableStateFlow(null)
    override val state = _state.asStateFlow()

    override suspend fun loadMyProfiles(): Result<List<ProfileInformation>, Throwable> =
        runSuspendCatching {
            api.loadProfiles().map(ProfileInformation::fromDto)
        }
            .recoverIf(
                predicate = { error ->
                    error is HttpException && error.code() == HTTP_NOT_FOUND
                }
            ) {
                emptyList()
            }
            .logW(TAG)
            .onSuccess { _state.emit(it) }

    override suspend fun loadProfileInformation(id: String): Result<ProfileInformation, Throwable> =
        runSuspendCatching {
            api.loadProfileInfo(id)
        }
            .logW(TAG)
            .map(ProfileInformation::fromDto)
            .onSuccess { _state.emit(listOf(it)) }

    override suspend fun deleteProfile(profileInformation: ProfileInformation) = runSuspendCatching {
        api.deleteProfile(profileInformation.id!!)
    }
        .logW(TAG)
        .onSuccess {
            _state.update { profiles ->
                if (profiles != null) {
                    profiles - profileInformation
                } else null
            }
        }

    companion object {
        private const val TAG = "RealProfileRepository"
    }
}
