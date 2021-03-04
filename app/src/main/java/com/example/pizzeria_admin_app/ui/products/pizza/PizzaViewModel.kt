package com.example.pizzeria_admin_app.ui.products.pizza

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pizzeria_admin_app.data.local.preferences.UserPreferences
import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.product.api.models.ProductPizza
import com.example.pizzeria_admin_app.data.usecases.products.pizza.CreatePizza
import com.example.pizzeria_admin_app.data.usecases.products.pizza.DeletePizza
import com.example.pizzeria_admin_app.data.usecases.products.pizza.GetPizzas
import com.example.pizzeria_admin_app.data.usecases.products.pizza.UpdatePizza
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.ui.common.AuthViewModel
import com.example.pizzeria_admin_app.utils.auth.TokenValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PizzaViewModel @ViewModelInject constructor(
    private val getPizzas: GetPizzas,
    private val createPizza: CreatePizza,
    private val updatePizza: UpdatePizza,
    private val deletePizza: DeletePizza,
    userPreferences: UserPreferences,
    tokenValidator: TokenValidator
) : AuthViewModel(userPreferences, tokenValidator) {
    val pizza = MutableLiveData<Resource<List<ProductPizza>?>>()

    var currentPizzaName: String? = null
    var currentPizzaNumber: String? = null
    var currentPizzaIngredients = MutableLiveData<List<String>>(listOf())
    var currentPizza24Price: String? = null
    var currentPizza30Price: String? = null
    var currentPizza40Price: String? = null
    var currentPizza50Price: String? = null

    var pizzaRequestResponse = Channel<Resource<GenericResponse?>>(Channel.CONFLATED)

    var pizzaDeletionRequestResponse = Channel<Resource<GenericResponse?>>(Channel.CONFLATED)

    fun setPizzaChannels() {
        pizzaRequestResponse = Channel(Channel.CONFLATED)

        pizzaDeletionRequestResponse = Channel(Channel.CONFLATED)
    }

    fun fetchPizza() {
        viewModelScope.launch(Dispatchers.IO) {
            getPizzas.getAllPizzas(token!!).collect {
                pizza.postValue(it)
            }
        }
    }

    fun removePizzaIngredient(ingredient: String) {
        currentPizzaIngredients.value = currentPizzaIngredients.value!!.filter { it !== ingredient }
    }

    fun createPizza() {
        viewModelScope.launch(Dispatchers.IO) {
            createPizza.createPizza(
                token!!,
                ProductPizza(
                    id = null,
                    number = currentPizzaNumber!!.toInt(),
                    name = currentPizzaName!!,
                    ingredients = currentPizzaIngredients.value!!,
                    size24Price = currentPizza24Price!!.toFloat(),
                    size30Price = currentPizza30Price!!.toFloat(),
                    size40Price = currentPizza40Price!!.toFloat(),
                    size50Price = currentPizza50Price!!.toFloat()
                )
            ).collect {
                pizzaRequestResponse.send(it)
            }
        }
    }

    fun updatePizza(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            updatePizza.updatePizza(
                token!!,
                id,
                ProductPizza(
                    id = null,
                    number = currentPizzaNumber!!.toInt(),
                    name = currentPizzaName!!,
                    ingredients = currentPizzaIngredients.value!!,
                    size24Price = currentPizza24Price!!.toFloat(),
                    size30Price = currentPizza30Price!!.toFloat(),
                    size40Price = currentPizza40Price!!.toFloat(),
                    size50Price = currentPizza50Price!!.toFloat()
                )
            ).collect {
                pizzaRequestResponse.send(it)
            }
        }
    }

    fun deletePizza(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePizza.deletePizza(token!!, id).collect {
                pizzaDeletionRequestResponse.send(it)
            }
        }
    }

    fun clearCurrentPizzaInfo() {
        currentPizzaName = null
        currentPizzaNumber = null
        currentPizzaIngredients.value = listOf()
        currentPizza24Price = null
        currentPizza30Price = null
        currentPizza40Price = null
        currentPizza50Price = null
    }
}