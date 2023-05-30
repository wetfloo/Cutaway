package io.wetfloo.cutaway.data.repository.createeditprofile

import io.wetfloo.cutaway.data.api.GeneralApi
import javax.inject.Inject

class RealCreateEditProfileRepository @Inject constructor(
    private val api: GeneralApi,
) : CreateEditProfileRepository
