package io.wetfloo.cutaway.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import io.wetfloo.cutaway.core.commonimpl.PreferencesStorage
import io.wetfloo.cutaway.data.model.profile.ProfilePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfilePreferencesStorage @Inject constructor(
    @ApplicationContext context: Context,
) : PreferencesStorage<ProfilePreferences>(
    context = context,
    preferencesName = "profile_prefs",
) {
    override val preferencesFlow: Flow<ProfilePreferences> = dataStore.data.map(::mapToType)

    suspend fun setProfileId(id: String) {
        setKey(
            key = stringPreferencesKey(Keys.PROFILE_ID),
            value = id,
        )
    }

    private fun mapToType(preferences: Preferences) = ProfilePreferences(
        selectedId = preferences[stringPreferencesKey(Keys.PROFILE_ID)],
    )

    private object Keys {
        const val PROFILE_ID = "PROFILE_ID"
    }
}
