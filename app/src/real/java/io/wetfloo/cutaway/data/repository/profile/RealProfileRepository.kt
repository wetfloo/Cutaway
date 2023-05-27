package io.wetfloo.cutaway.data.repository.profile

import android.util.Log
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.onFailure
import io.wetfloo.cutaway.core.common.runSuspendCatching
import io.wetfloo.cutaway.data.api.GeneralApi
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.data.preferences.ProfilePreferencesStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RealProfileRepository @Inject constructor(
    private val api: GeneralApi,
    private val profilePreferencesStorage: ProfilePreferencesStorage,
) : ProfileRepository {
    private val _state: MutableStateFlow<ProfileInformation?> = MutableStateFlow(null)
    override val state = _state.asStateFlow()

    override suspend fun loadProfileInformation(): Result<ProfileInformation, Throwable> {
        val preferredId = profilePreferencesStorage.prefs().selectedId
        return runSuspendCatching {
            if (preferredId != null) {
                api.loadProfileInfo(preferredId)
            } else {
                api.loadProfiles().first()
            }
        }.map(ProfileInformation::fromDto).onFailure { Log.w(TAG, it) }
    }

    override suspend fun loadProfiles(): Result<List<ProfileInformation>, Throwable> = runSuspendCatching {
        api.loadProfiles().map(ProfileInformation::fromDto)
    }

    companion object {
        private const val TAG = "RealProfileRepository"
    }
}
