package com.example.pizzeria_admin_app.ui.products.salad

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pizzeria_admin_app.data.local.preferences.UserPreferences
import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.product.api.models.Salad
import com.example.pizzeria_admin_app.data.usecases.products.salad.CreateSalad
import com.example.pizzeria_admin_app.data.usecases.products.salad.DeleteSalad
import com.example.pizzeria_admin_app.data.usecases.products.salad.GetSalads
import com.example.pizzeria_admin_app.data.usecases.products.salad.UpdateSalad
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.ui.common.AuthViewModel
import com.example.pizzeria_admin_app.utils.auth.TokenValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SaladViewModel @ViewModelInject constructor(
    private val getSalad: GetSalads,
    private val createSalad: CreateSalad,
    private val updateSalad: UpdateSalad,
    private val deleteSalad: DeleteSalad,
    userPreferences: UserPreferences,
    tokenValidator: TokenValidator
) : AuthViewModel(userPreferences, tokenValidator) {
    val salad = MutableLiveData<Resource<List<Salad>?>>()
    var saladRequestResponse = Channel<Resource<GenericResponse?>>(Channel.CONFLATED)

    var saladDeletionRequestResponse = Channel<Resource<GenericResponse?>>(Channel.CONFLATED)

    fun fetchSalads() {
        viewModelScope.launch(Dispatchers.IO) {
            getSalad.getSalads(token!!).collect {
                salad.postValue(it)
            }
        }
    }

    var currentSaladName: String? = null
    var currentSaladNumber: String? = null
    var currentSaladIngredients = MutableLiveData<List<String>>(listOf())
    var currentSaladPrice: String? = null

    fun setSaladChannels() {
        saladRequestResponse = Channel(Channel.CONFLATED)
        saladDeletionRequestResponse = Channel(Channel.CONFLATED)
    }

    fun removeSaladIngredient(ingredient: String) {
        currentSaladIngredients.value =
            currentSaladIngredients.value!!.filter { it !== ingredient }
    }

    fun createSalad() {
        viewModelScope.launch(Dispatchers.IO) {
            createSalad.createSalad(
                token!!,
                Salad(
                    id = null,
                    number = currentSaladNumber!!.toInt(),
                    name = currentSaladName!!,
                    ingredients = currentSaladIngredients.value!!,
                    price = currentSaladPrice!!.toFloat(),
                )
            ).collect {
                saladRequestResponse.send(it)
            }
        }
    }

    fun updateSalad(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSalad.updateSalad(
                token!!,
                id,
                Salad(
                    id = null,
                    number = currentSaladNumber!!.toInt(),
                    name = currentSaladName!!,
                    ingredients = currentSaladIngredients.value!!,
                    price = currentSaladPrice!!.toFloat(),
                )
            ).collect {
                saladRequestResponse.send(it)
            }
        }
    }

    fun deleteSalad(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteSalad.deleteSalad(token!!, id).collect {
                saladDeletionRequestResponse.send(it)
            }
        }
    }

    fun clearCurrentSaladInfo() {
        currentSaladName = null
        currentSaladNumber = null
        currentSaladIngredients.value = listOf()
        currentSaladPrice = null
    }
}