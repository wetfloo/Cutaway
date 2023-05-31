package io.wetfloo.cutaway.data

import io.wetfloo.cutaway.data.preferences.AuthPreferencesStorage
import javax.inject.Inject

class LoginStateResetter @Inject constructor(
    private val authStorage: AuthPreferencesStorage,
) {
    suspend fun reset() {
        authStorage.setToken(null)
    }
}
