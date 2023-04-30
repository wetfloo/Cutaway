package io.wetfloo.cutaway.data.repository.profile

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.misc.utils.demo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class FakeProfileRepository @Inject constructor() : ProfileRepository {
    override val state: StateFlow<Result<ProfileInformation, Throwable>> =
        MutableStateFlow(profileInformationResult).asStateFlow()

    override suspend fun loadProfileInformation() = profileInformationResult

    private val profileInformationResult
        get() = Ok(ProfileInformation.demo)
}
