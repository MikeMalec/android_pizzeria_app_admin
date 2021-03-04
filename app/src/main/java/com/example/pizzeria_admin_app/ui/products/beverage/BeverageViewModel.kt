package com.example.pizzeria_admin_app.ui.products.beverage

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pizzeria_admin_app.data.local.preferences.UserPreferences
import com.example.pizzeria_admin_app.data.local.product.AlcoholGroup
import com.example.pizzeria_admin_app.data.remote.product.api.models.Beverage
import com.example.pizzeria_admin_app.data.usecases.products.beverage.CreateBeverage
import com.example.pizzeria_admin_app.data.usecases.products.beverage.DeleteBeverage
import com.example.pizzeria_admin_app.data.usecases.products.beverage.GetBeverages
import com.example.pizzeria_admin_app.data.usecases.products.beverage.UpdateBeverage
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.ui.common.AuthViewModel
import com.example.pizzeria_admin_app.utils.auth.TokenValidator
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BeverageViewModel @ViewModelInject constructor(
    private val getBeverages: GetBeverages,
    private val createBeverage: CreateBeverage,
    private val updateBeverage: UpdateBeverage,
    private val deleteBeverage: DeleteBeverage,
    userPreferences: UserPreferences,
    tokenValidator: TokenValidator
) : AuthViewModel(userPreferences, tokenValidator) {
    val hotBeverages = MutableLiveData<List<Beverage>>()
    val coldBeverages = MutableLiveData<List<Beverage>>()
    val alcoholGroups = MutableLiveData<List<AlcoholGroup>>()
    fun fetchBeverages() {
        viewModelScope.launch(
            IO
        ) {
            getBeverages.getBeverages(token!!).collect {
                if (it is Resource.Success) {
                    it.data?.also { data ->
                        val grouped = data.groupBy { it.type }
                        grouped["cold"]?.also { cold -> coldBeverages.postValue(cold) }
                        grouped["hot"]?.also { hot -> hotBeverages.postValue(hot) }
                        grouped["alcohol"]?.also { alcohols ->
                            alcohols.groupBy { it.alcoholType }.map { (groupName, groupValue) ->
                                AlcoholGroup(groupName!!, groupValue)
                            }.also { alcoGroups -> alcoholGroups.postValue(alcoGroups) }
                        }
                    }
                }
            }
        }
    }

    var name: String? = null
    var number: String? = null
    var beverageType: String? = null
    var orderable: Boolean = false
    var beverageSize: String? = null
    var beveragePrice: String? = null
    var alcoholType: String? = null
    var alcoholSize: String? = null
    var alcoholPrice: String? = null
    var alcoholSmallPrice: String? = null
    var alcoholBigPrice: String? = null

    fun clearInfo() {
        name = null
        number = null
        beverageType = null
        orderable = false
        beverageSize = null
        beveragePrice = null
        alcoholType = null
        alcoholSize = null
        alcoholPrice = null
        alcoholSmallPrice = null
        alcoholBigPrice = null
    }

    fun createBeverage() {
        viewModelScope.launch(IO) {
            var size: String? = null
            var price: String? = null
            when (beverageType) {
                "cold", "hot" -> {
                    size = beverageSize
                    price = beveragePrice
                }
                "alcohol" -> {
                    size = alcoholSize
                    price = alcoholPrice
                }
            }
            createBeverage.createBeverage(
                token!!,
                Beverage(
                    id = null,
                    name = name!!,
                    type = beverageType!!,
                    alcoholType = alcoholType,
                    size = size,
                    smallPrice = alcoholSmallPrice?.toFloat(),
                    bigPrice = alcoholBigPrice?.toFloat(),
                    price = price?.toFloat(),
                    orderable = orderable,
                )
            ).collect {
                Log.d("XXX", "CREATE RESP = $it")
            }
        }
    }

    fun updateBeverage(id: Int) {
        viewModelScope.launch(IO) {
            var size: String? = null
            var price: String? = null
            when (beverageType) {
                "cold", "hot" -> {
                    size = beverageSize
                    price = beveragePrice
                }
                "alcohol" -> {
                    size = alcoholSize
                    price = alcoholPrice
                }
            }
            updateBeverage.updateBeverage(
                token!!,
                id,
                Beverage(
                    id = null,
                    name = name!!,
                    number = number,
                    type = beverageType!!,
                    alcoholType = alcoholType,
                    size = size,
                    smallPrice = alcoholSmallPrice?.toFloat(),
                    bigPrice = alcoholBigPrice?.toFloat(),
                    price = price?.toFloat(),
                    orderable = orderable,
                )
            ).collect {
                Log.d("XXX", "UPDATE RESP = $it")
            }
        }
    }

    fun deleteBeverage(id: Int) {
        viewModelScope.launch(IO) {
            deleteBeverage.deleteBeverage(token!!, id).collect {
                Log.d("XXX", "DELETE BEVERAGE RESP $it")
            }
        }
    }
}