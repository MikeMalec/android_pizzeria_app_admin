package com.example.pizzeria_admin_app.data.remote.auth

import com.example.pizzeria_admin_app.data.remote.auth.models.LoginRequest
import javax.inject.Inject

class AuthService @Inject constructor(val authApi: AuthApi) {
    suspend fun login(loginRequest: LoginRequest) = authApi.login(loginRequest)
}