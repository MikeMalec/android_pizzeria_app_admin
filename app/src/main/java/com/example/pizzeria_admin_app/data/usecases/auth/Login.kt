package com.example.pizzeria_admin_app.data.usecases.auth

import com.example.pizzeria_admin_app.data.remote.auth.AuthService
import com.example.pizzeria_admin_app.data.remote.auth.models.LoginRequest
import com.example.pizzeria_admin_app.data.remote.auth.models.LoginResponse
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.Resource.Loading
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Login @Inject constructor(val authService: AuthService) {
    fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse?>> = flow {
        emit(Loading())
        val response = safeApiCall(IO) { authService.login(loginRequest) }
        emit(response)
    }
}