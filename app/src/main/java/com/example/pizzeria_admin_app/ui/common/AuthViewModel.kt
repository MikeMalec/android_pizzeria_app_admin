package com.example.pizzeria_admin_app.ui.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzeria_admin_app.data.local.preferences.UserPreferences
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.utils.auth.TokenValidator
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Error

abstract class AuthViewModel(
    protected val userPreferences: UserPreferences,
    protected val tokenValidator: TokenValidator
) : ViewModel() {

    val tokenFlow = userPreferences.getToken()
    var token: String? = null

    init {
        observeToken()
    }

    private fun observeToken() {
        viewModelScope.launch(IO) {
            userPreferences.getToken().collect {
                token = it
            }
        }
    }

    protected fun checkResponse(response: Resource<*>) {
        if (response is Resource.Error) {
            viewModelScope.launch {
                tokenValidator.checkIfInvalidTokenFromRequestResponse(response as Resource.Error)
            }
        }
    }
}