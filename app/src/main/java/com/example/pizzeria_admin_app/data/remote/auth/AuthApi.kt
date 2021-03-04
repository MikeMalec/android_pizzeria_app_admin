package com.example.pizzeria_admin_app.data.remote.auth

import com.example.pizzeria_admin_app.data.remote.auth.models.LoginRequest
import com.example.pizzeria_admin_app.data.remote.auth.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}