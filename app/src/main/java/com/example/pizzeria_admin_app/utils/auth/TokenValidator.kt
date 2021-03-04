package com.example.pizzeria_admin_app.utils.auth

import android.util.Log
import com.example.pizzeria_admin_app.data.local.preferences.UserPreferences
import com.example.pizzeria_admin_app.data.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenValidator @Inject constructor(val userPreferences: UserPreferences) {
    suspend fun checkIfInvalidTokenFromRequestResponse(response: Resource.Error) {
        response.error?.also {
            if (it.contains("Not authorized")) {
                userPreferences.clearToken()
            }
        }
    }
}