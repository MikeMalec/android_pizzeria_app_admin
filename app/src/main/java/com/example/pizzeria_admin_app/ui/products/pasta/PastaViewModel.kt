package com.example.pizzeria_admin_app.ui.products.pasta

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pizzeria_admin_app.data.local.preferences.UserPreferences
import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.product.api.models.Pasta
import com.example.pizzeria_admin_app.data.usecases.products.pasta.CreatePasta
import com.example.pizzeria_admin_app.data.usecases.products.pasta.DeletePasta
import com.example.pizzeria_admin_app.data.usecases.products.pasta.GetPastas
import com.example.pizzeria_admin_app.data.usecases.products.pasta.UpdatePasta
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.ui.common.AuthViewModel
import com.example.pizzeria_admin_app.utils.auth.TokenValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PastaViewModel @ViewModelInject constructor(
    private val getPastas: GetPastas,
    private val createPasta: CreatePasta,
    private val updatePasta: UpdatePasta,
    private val deletePasta: DeletePasta,
    userPreferences: UserPreferences,
    tokenValidator: TokenValidator
) : AuthViewModel(userPreferences, tokenValidator) {
    val pasta = MutableLiveData<Resource<List<Pasta>?>>()
    var pastaRequestResponse = Channel<Resource<GenericResponse?>>(Channel.CONFLATED)

    var pastaDeletionRequestResponse = Channel<Resource<GenericResponse?>>(Channel.CONFLATED)

    fun fetchPastas() {
        viewModelScope.launch(Dispatchers.IO) {
            getPastas.getPastas(token!!).collect {
                pasta.postValue(it)
            }
        }
    }

    var currentPastaName: String? = null
    var currentPastaNumber: String? = null
    var currentPastaIngredients = MutableLiveData<List<String>>(listOf())
    var currentPastaSmallPrice: String? = null
    var currentPastaBigPrice: String? = null

    fun setPastaChannels() {
        pastaRequestResponse = Channel(Channel.CONFLATED)
        pastaDeletionRequestResponse = Channel(Channel.CONFLATED)
    }

    fun removePastaIngredient(ingredient: String) {
        currentPastaIngredients.value =
            currentPastaIngredients.value!!.filter { it !== ingredient }
    }

    fun createPasta() {
        viewModelScope.launch(Dispatchers.IO) {
            createPasta.createPasta(
                token!!,
                Pasta(
                    id = null,
                    number = currentPastaNumber!!.toInt(),
                    name = currentPastaName!!,
                    ingredients = currentPastaIngredients.value!!,
                    smallPrice = currentPastaSmallPrice!!.toFloat(),
                    bigPrice = currentPastaBigPrice!!.toFloat(),
                )
            ).collect {
                pastaRequestResponse.send(it)
            }
        }
    }

    fun updatePasta(id: Int) {
        Log.d("XXX", "updatePas")
        viewModelScope.launch(Dispatchers.IO) {
            updatePasta.updatePasta(
                token!!,
                id,
                Pasta(
                    id = null,
                    number = currentPastaNumber!!.toInt(),
                    name = currentPastaName!!,
                    ingredients = currentPastaIngredients.value!!,
                    smallPrice = currentPastaSmallPrice!!.toFloat(),
                    bigPrice = currentPastaBigPrice!!.toFloat(),
                )
            ).collect {
                pastaRequestResponse.send(it)
            }
        }
    }

    fun deletePasta(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePasta.deletePasta(token!!, id).collect {
                pastaDeletionRequestResponse.send(it)
            }
        }
    }

    fun clearCurrentPastaInfo() {
        currentPastaName = null
        currentPastaNumber = null
        currentPastaIngredients.value = listOf()
        currentPastaSmallPrice = null
        currentPastaBigPrice = null
    }
}