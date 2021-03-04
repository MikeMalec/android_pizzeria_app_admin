package com.example.pizzeria_admin_app.ui.order

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pizzeria_admin_app.data.local.preferences.UserPreferences
import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.order.api.models.Order
import com.example.pizzeria_admin_app.data.usecases.order.SetOrderStatus
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.ui.common.AuthViewModel
import com.example.pizzeria_admin_app.utils.auth.TokenValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OrderViewModel @ViewModelInject constructor(
    private val setOrderStatus: SetOrderStatus, userPreferences: UserPreferences,
    tokenValidator: TokenValidator
) : AuthViewModel(userPreferences, tokenValidator) {

    val orderStatus = MutableLiveData<String>()
    lateinit var order: Order

    val orderStatusRequestState = Channel<Resource<GenericResponse?>>(Channel.CONFLATED)

    val orderStateChanged = Channel<Boolean>(Channel.CONFLATED)

    fun initOrder(order: Order) {
        this.order = order
        orderStatus.postValue(order.orderInfo.status)
    }

    fun setOrderStatus(status: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setOrderStatus.setOrderStatus(token!!, order.orderInfo.id, status).collect {
                orderStatusRequestState.send(it)
                if (it is Resource.Success) {
                    when (status) {
                        "accepted" -> {
                            order.orderInfo.status = "accepted"
                            orderStatus.postValue("accepted")
                        }
                        "canceled" -> {
                            order.orderInfo.status = "canceled"
                            orderStatus.postValue("canceled")
                        }
                    }
                    orderStateChanged.send(true)
                }
            }
        }
    }
}