package io.wetfloo.cutaway.core.commonimpl

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


/**
 * Base preferences storage
 */
abstract class PreferencesStorage<P> constructor(
    protected val context: Context,
    preferencesName: String,
) {
    /**
     * Should be implemented by delegating it to [preferencesDataStore]
     */
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(preferencesName)
    protected val dataStore
        get() = context.dataStore

    abstract val preferencesFlow: Flow<P>

    suspend fun prefs() = preferencesFlow.first()

    protected suspend fun <VT> setKey(key: Preferences.Key<VT>, value: VT?): Preferences {
        Log.d(TAG, "Setting preferences key $key with value $value")
        return context.dataStore.edit { preferences ->
            if (value != null) {
                preferences[key] = value
            } else {
                preferences.remove(key)
            }
        }
    }

    companion object {
        private const val TAG = "PreferencesStorage"
    }
}
