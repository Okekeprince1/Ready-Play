package com.example.readyplay.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userPreferencesDataStore by preferencesDataStore(name = "user_preferences")

class UserDataStore(private val context: Context) {
    val isLoggedIn: Flow<Boolean> =
        context.userPreferencesDataStore.data
            .map { preferences ->
                preferences[IS_LOGGED_IN] ?: false
            }

    suspend fun saveLoginState(isLoggedIn: Boolean) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    companion object PreferencesKeys {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }
}
