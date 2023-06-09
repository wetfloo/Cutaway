package io.wetfloo.cutaway.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import io.wetfloo.cutaway.core.commonimpl.PreferencesStorage
import io.wetfloo.cutaway.data.model.auth.AuthPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPreferencesStorage @Inject constructor(
    @ApplicationContext context: Context,
) : PreferencesStorage<AuthPreferences>(
    context = context,
    preferencesName = "auth_prefs",
) {
    override val preferencesFlow: Flow<AuthPreferences> = dataStore.data.map(::mapToType)

    suspend fun setToken(token: String?) {
        setKey(
            key = stringPreferencesKey(Keys.TOKEN),
            value = token,
        )
    }

    private fun mapToType(preferences: Preferences) = AuthPreferences(
        token = preferences[stringPreferencesKey(Keys.TOKEN)],
    )

    private object Keys {
        const val TOKEN = "TOKEN"
    }
}
