package io.wetfloo.cutaway.data.repository.profile

import com.github.michaelbull.result.Ok
import io.wetfloo.cutaway.core.common.DispatcherProvider
import io.wetfloo.cutaway.core.common.supervisor
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.misc.utils.demo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

/**
 * This repository creates profile info after delay,
 * and then stores it for the entire lifetime.
 */
class FakeProfileRepository @Inject constructor(
    dispatchers: DispatcherProvider,
) : ProfileRepository {
    private val coroutineScope = dispatchers.default.supervisor()

    private val _state: MutableStateFlow<ProfileInformation?> =
        MutableStateFlow(null)
    override val state = _state.asStateFlow()

    override suspend fun loadProfileInformation(id: String?) = state
        .filterNotNull()
        .first()
        .let(::Ok)

    init {
        coroutineScope.launch {
            delay(5.seconds)
            _state.value = ProfileInformation.demo
        }
    }
}
