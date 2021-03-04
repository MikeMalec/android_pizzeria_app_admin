package com.example.pizzeria_admin_app.ui.products.burger

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pizzeria_admin_app.data.local.preferences.UserPreferences
import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.product.api.models.Burger
import com.example.pizzeria_admin_app.data.usecases.products.burger.CreateBurger
import com.example.pizzeria_admin_app.data.usecases.products.burger.DeleteBurger
import com.example.pizzeria_admin_app.data.usecases.products.burger.GetBurgers
import com.example.pizzeria_admin_app.data.usecases.products.burger.UpdateBurger
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.ui.common.AuthViewModel
import com.example.pizzeria_admin_app.utils.auth.TokenValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BurgerViewModel @ViewModelInject constructor(
    private val getBurgers: GetBurgers,
    private val createBurger: CreateBurger,
    private val updateBurger: UpdateBurger,
    private val deleteBurger: DeleteBurger,
    userPreferences: UserPreferences,
    tokenValidator: TokenValidator
) : AuthViewModel(userPreferences, tokenValidator) {
    val burger = MutableLiveData<Resource<List<Burger>?>>()
    var burgerRequestResponse = Channel<Resource<GenericResponse?>>(Channel.CONFLATED)

    var burgerDeletionRequestResponse = Channel<Resource<GenericResponse?>>(Channel.CONFLATED)

    fun fetchBurgers() {
        viewModelScope.launch(Dispatchers.IO) {
            getBurgers.getBurgers(token!!).collect {
                burger.postValue(it)
            }
        }
    }

    var currentBurgerName: String? = null
    var currentBurgerNumber: String? = null
    var currentBurgerIngredients = MutableLiveData<List<String>>(listOf())
    var currentBurgerSoloPrice: String? = null
    var currentBurgerSetPrice: String? = null

    fun setBurgerChannels() {
        burgerRequestResponse = Channel(Channel.CONFLATED)
        burgerDeletionRequestResponse = Channel(Channel.CONFLATED)
    }

    fun removeBurgerIngredient(ingredient: String) {
        currentBurgerIngredients.value =
            currentBurgerIngredients.value!!.filter { it !== ingredient }
    }

    fun createBurger() {
        viewModelScope.launch(Dispatchers.IO) {
            createBurger.createBurger(
                token!!,
                Burger(
                    id = null,
                    number = currentBurgerNumber!!.toInt(),
                    name = currentBurgerName!!,
                    ingredients = currentBurgerIngredients.value!!,
                    soloPrice = currentBurgerSoloPrice!!.toFloat(),
                    setPrice = currentBurgerSetPrice!!.toFloat(),
                )
            ).collect {
                burgerRequestResponse.send(it)
            }
        }
    }

    fun updateBurger(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            updateBurger.updateBurger(
                token!!,
                id,
                Burger(
                    id = null,
                    number = currentBurgerNumber!!.toInt(),
                    name = currentBurgerName!!,
                    ingredients = currentBurgerIngredients.value!!,
                    soloPrice = currentBurgerSoloPrice!!.toFloat(),
                    setPrice = currentBurgerSetPrice!!.toFloat(),
                )
            ).collect {
                burgerRequestResponse.send(it)
            }
        }
    }

    fun deleteBurger(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteBurger.deleteBurger(token!!, id).collect {
                burgerDeletionRequestResponse.send(it)
            }
        }
    }

    fun clearCurrentBurgerInfo() {
        currentBurgerName = null
        currentBurgerNumber = null
        currentBurgerIngredients.value = listOf()
        currentBurgerSoloPrice = null
        currentBurgerSetPrice = null
    }
}