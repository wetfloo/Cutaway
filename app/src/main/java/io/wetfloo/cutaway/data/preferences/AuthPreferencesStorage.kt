package io.wetfloo.cutaway.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.wetfloo.cutaway.core.commonimpl.PreferencesManager
import io.wetfloo.cutaway.data.model.auth.AuthPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPreferencesStorage @Inject constructor(
    @ApplicationContext context: Context,
) : PreferencesManager<AuthPreferences>(context) {
    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)

    override val preferencesFlow: Flow<AuthPreferences> = context
        .dataStore
        .data
        .map(::mapToType)

    suspend fun setToken(token: String?) {
        setKey(
            key = stringPreferencesKey(Keys.TOKEN),
            value = token,
        )
    }

    private fun mapToType(preferences: Preferences) = AuthPreferences(
        token = preferences[stringPreferencesKey(Keys.TOKEN)],
    )


    companion object {
        private const val PREFERENCES_NAME = "auth_prefs"
    }

    private object Keys {
        const val TOKEN = "TOKEN"
    }
}
