package com.root14.teamup.data;

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    "data_store"
)

/**
 * This class provides methods for managing shared preferences data.
 */
class PrefDataStoreManager private constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private var instance: PrefDataStoreManager? = null

        /**
         * Provides a singleton instance of the PrefDataStoreManager class.
         *
         * @param application The application context to access the shared preferences.
         * @return The PrefDataStoreManager instance.
         */
        fun getInstance(application: Application): PrefDataStoreManager {
            if (instance == null) {
                instance = PrefDataStoreManager(application.applicationContext.dataStore)
            }
            return instance!!
        }
    }

    /**
     * Saves a string value to the shared preferences file.
     *
     * @param key The name of the preference to save the data to.
     * @param data The string value to save.
     * @return A boolean value indicating whether the save was successful.
     */
    suspend fun saveStringData(key: String, data: String): Boolean {
        return try {
            // Edit the preferences and set the value of the specified key to the given data.
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = data
            }
            true
        } catch (exception: Exception) {
            // An error occurred while saving the data. Log the error and return false.
            println(exception.stackTrace)
            false
        }
    }

    /**
     * Reads a string value from the shared preferences file.
     *
     * @param key The name of the preference to retrieve the data from.
     * @return A Flow object that emits the value of the preference whenever it changes.
     */
    fun readStringData(key: String) =
        dataStore.data.map { preferences ->
            // Get the value of the specified key from the preferences.
            preferences[stringPreferencesKey(key)]
        }
}