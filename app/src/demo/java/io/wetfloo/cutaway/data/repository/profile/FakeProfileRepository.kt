package io.wetfloo.cutaway.data.repository.profile

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.misc.utils.demo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeProfileRepository @Inject constructor() : ProfileRepository {
    override val state: Flow<Result<ProfileInformation, Throwable>>
        get() = flowOf(Ok(ProfileInformation.demo))

    override suspend fun loadProfileInformation(): Result<ProfileInformation, Throwable> =
        Ok(ProfileInformation.demo)
}
