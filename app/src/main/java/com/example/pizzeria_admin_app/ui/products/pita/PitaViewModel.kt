package com.example.pizzeria_admin_app.ui.products.pita

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pizzeria_admin_app.data.local.preferences.UserPreferences
import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.product.api.models.Pita
import com.example.pizzeria_admin_app.data.usecases.products.pita.CreatePita
import com.example.pizzeria_admin_app.data.usecases.products.pita.DeletePita
import com.example.pizzeria_admin_app.data.usecases.products.pita.GetPitas
import com.example.pizzeria_admin_app.data.usecases.products.pita.UpdatePita
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.ui.common.AuthViewModel
import com.example.pizzeria_admin_app.utils.auth.TokenValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PitaViewModel @ViewModelInject constructor(
    private val getPitas: GetPitas,
    private val createPita: CreatePita,
    private val updatePita: UpdatePita,
    private val deletePita: DeletePita,
    userPreferences: UserPreferences,
    tokenValidator: TokenValidator
) : AuthViewModel(userPreferences, tokenValidator) {
    val pitas = MutableLiveData<Resource<List<Pita>?>>()
    var pitaRequestResponse = Channel<Resource<GenericResponse?>>(Channel.CONFLATED)

    var pitaDeletionRequestResponse = Channel<Resource<GenericResponse?>>(Channel.CONFLATED)

    fun fetchPitas() {
        viewModelScope.launch(Dispatchers.IO) {
            getPitas.getPitas(token!!).collect {
                pitas.postValue(it)
            }
        }
    }

    var currentPitaName: String? = null
    var currentPitaNumber: String? = null
    var currentPitaIngredients = MutableLiveData<List<String>>(listOf())
    var currentPitaSmallPrice: String? = null
    var currentPitaBigPrice: String? = null

    fun setPitaChannels() {
        pitaRequestResponse = Channel(Channel.CONFLATED)
        pitaDeletionRequestResponse = Channel(Channel.CONFLATED)
    }

    fun removePitaIngredient(ingredient: String) {
        currentPitaIngredients.value =
            currentPitaIngredients.value!!.filter { it !== ingredient }
    }

    fun createPita() {
        viewModelScope.launch(Dispatchers.IO) {
            createPita.createPita(
                token!!,
                Pita(
                    id = null,
                    number = currentPitaNumber!!.toInt(),
                    name = currentPitaName!!,
                    ingredients = currentPitaIngredients.value!!,
                    smallPrice = currentPitaSmallPrice!!.toFloat(),
                    bigPrice = currentPitaBigPrice!!.toFloat(),
                )
            ).collect {
                pitaRequestResponse.send(it)
            }
        }
    }

    fun updatePita(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            updatePita.updatePita(
                token!!,
                id,
                Pita(
                    id = null,
                    number = currentPitaNumber!!.toInt(),
                    name = currentPitaName!!,
                    ingredients = currentPitaIngredients.value!!,
                    smallPrice = currentPitaSmallPrice!!.toFloat(),
                    bigPrice = currentPitaBigPrice!!.toFloat(),
                )
            ).collect {
                pitaRequestResponse.send(it)
            }
        }
    }

    fun deletePita(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePita.deletePita(token!!, id).collect {
                pitaDeletionRequestResponse.send(it)
            }
        }
    }

    fun clearCurrentPitaInfo() {
        currentPitaName = null
        currentPitaNumber = null
        currentPitaIngredients.value = listOf()
        currentPitaSmallPrice = null
        currentPitaBigPrice = null
    }
}