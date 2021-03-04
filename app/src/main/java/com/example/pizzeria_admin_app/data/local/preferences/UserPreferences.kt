package com.example.pizzeria_admin_app.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.pizzeria_admin_app.utils.constants.PREFERENCES_TOKEN
import com.example.pizzeria_admin_app.utils.constants.USER_PREFERENCES
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val preferences = context.createDataStore(name = USER_PREFERENCES)

    companion object {
        val tokenKey = preferencesKey<String>(PREFERENCES_TOKEN)
    }

    suspend fun clearToken() {
        preferences.edit { prefs ->
            prefs[tokenKey] = ""
        }
    }

    suspend fun saveToken(token: String) {
        preferences.edit { prefs ->
            prefs[tokenKey] = token
        }
    }

    fun getToken(): Flow<String> {
        return preferences.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { prefs ->
            "Bearer ${prefs[tokenKey]}"
        }
    }
}