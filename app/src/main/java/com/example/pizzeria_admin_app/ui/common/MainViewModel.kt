package com.example.pizzeria_admin_app.ui.common

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pizzeria_admin_app.data.local.preferences.UserPreferences
import com.example.pizzeria_admin_app.data.remote.auth.models.LoginRequest
import com.example.pizzeria_admin_app.data.remote.auth.models.LoginResponse
import com.example.pizzeria_admin_app.data.remote.order.api.models.Order
import com.example.pizzeria_admin_app.data.remote.order.service.OrderService
import com.example.pizzeria_admin_app.data.remote.time.Time
import com.example.pizzeria_admin_app.data.usecases.auth.Login
import com.example.pizzeria_admin_app.data.usecases.order.GetFilteredOldOrders
import com.example.pizzeria_admin_app.data.usecases.order.GetNewOrders
import com.example.pizzeria_admin_app.data.usecases.order.GetOldOrders
import com.example.pizzeria_admin_app.data.usecases.time.GetTime
import com.example.pizzeria_admin_app.data.usecases.time.UpdateTime
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.Resource.Success
import com.example.pizzeria_admin_app.utils.auth.TokenValidator
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.ceil

class MainViewModel @ViewModelInject constructor(
    private val getNewOrders: GetNewOrders,
    private val getOldOrders: GetOldOrders,
    private val getFilteredOldOrders: GetFilteredOldOrders,
    private val getTime: GetTime,
    private val updateTime: UpdateTime,
    private val login: Login,
    userPreferences: UserPreferences,
    tokenValidator: TokenValidator
) : AuthViewModel(userPreferences, tokenValidator) {

    val newOrders = MutableStateFlow<List<Order>>(listOf())

    fun startActionsForAuthenticated() {
        fetchNewOrders()
        observeNewOrdersFromSocket()
        observeNewOrdersInterval()
    }

    private fun observeNewOrdersFromSocket() {
        viewModelScope.launch(IO) {
            OrderService.orders.consumeAsFlow().collect {
                val orders = mutableListOf<Order>()
                newOrders.value?.also { orders.addAll(it) }
                orders.add(it)
                newOrders.value = orders
            }
        }
    }

    private fun observeNewOrdersInterval() {
        viewModelScope.launch(IO) {
            OrderService.newOrdersIntervalData.consumeAsFlow().collect {
                newOrders.value = it
            }
        }
    }

    fun fetchNewOrders() {
        viewModelScope.launch(IO) {
            getNewOrders.getNewOrders(token!!).collect {
                checkResponse(it)
                if (it is Success) {
                    it.data?.also { orders ->
                        newOrders.value = orders.orders
                    }
                }
            }
        }
    }

    fun removeOrderFromNewOrders(order: Order) {
        newOrders.value?.run {
            val filtered = this.filter { it != order }
            newOrders.value = filtered
        }
        addOrderToOldOrders(order)
    }

    private fun addOrderToOldOrders(order: Order) {
        val orders = mutableListOf<Order>()
        orders.add(order)
        oldOrders.value?.also { orders.addAll(it) }
        oldOrders.postValue(orders)
    }

    val oldOrders = MutableLiveData<List<Order>>()
    private var fetchedOldOrders = listOf<Order>()
    private var page = 0
    private var pages = 1
    private var fetching = false

    fun fetchOldOrdersIfEmpty() {
        if (oldOrders.value == null || oldOrders.value!!.isEmpty()) {
            fetchOldOrders()
        }
    }

    fun fetchOldOrders() {
        viewModelScope.launch(IO) {
            if (page <= pages && !fetching) {
                fetching = true
                page++
                var lastFetched: String? = null
                if (fetchedOldOrders.isNotEmpty()) {
                    lastFetched = fetchedOldOrders[fetchedOldOrders.size - 1].orderInfo.updatedAt
                }
                getOldOrders.getOldOrders(token!!, lastFetched).collect {
                    if (it is Success) {
                        it.data?.also { resp ->
                            pages = ceil(resp.results.toDouble() / 20).toInt()
                            val orders = mutableListOf<Order>()
                            if (oldOrders.value != null) {
                                orders.addAll(oldOrders.value!!)
                            }
                            orders.addAll(resp.orders)
                            fetchedOldOrders = orders
                            oldOrders.postValue(orders)
                        }
                    }
                    fetching = false
                }
            }
        }
    }

    fun resetOldOrders() {
        fetchedOldOrders = listOf()
        page = 0
        pages = 1
        fetching = false
        fetchOldOrders()
    }

    // filtered old orders

    var query: String? = null

    val queryChannel = Channel<String>(Channel.CONFLATED)

    init {
        observeQueryChannel()
    }

    private fun observeQueryChannel() {
        viewModelScope.launch(IO) {
            queryChannel.consumeAsFlow().debounce(500L).mapLatest { it }
                .collect {
                    clearFilterData()
                    query = it
                    fetchFilteredOldOrders(it)
                }
        }
    }

    val filteredOldOrders = MutableLiveData<List<Order>>()
    private var filteredFetchedOldOrders = listOf<Order>()
    private var filteredPage = 0
    private var filteredPages = 1
    private var filteredFetching = false

    private fun clearFilterData() {
        filteredFetchedOldOrders = listOf()
        filteredPage = 0
        filteredPages = 1
        filteredFetching = false
    }

    fun filteringOldOrders(): Boolean {
        return query != null
    }

    fun fetchFilteredOldOrders(query: String) {
        viewModelScope.launch(IO) {
            if (filteredPage <= filteredPages && !filteredFetching) {
                filteredFetching = true
                filteredPage++
                var lastFetched: String? = null
                if (filteredFetchedOldOrders.isNotEmpty()) {
                    lastFetched =
                        filteredFetchedOldOrders[filteredFetchedOldOrders.size - 1].orderInfo.updatedAt
                }
                getFilteredOldOrders.getFilteredOldOrders(token!!, query, lastFetched).collect {
                    if (it is Success) {
                        it.data?.also { resp ->
                            filteredPages = ceil(resp.results.toDouble() / 20).toInt()
                            val orders = mutableListOf<Order>()
                            orders.addAll(filteredFetchedOldOrders)
                            orders.addAll(resp.orders)
                            filteredFetchedOldOrders = orders
                            filteredOldOrders.postValue(orders)
                        }
                    }
                    filteredFetching = false
                }
            }
        }
    }

    fun clearOrderFiltering() {
        filteredFetchedOldOrders = listOf()
        filteredPage = 0
        filteredPages = 1
        filteredFetching = false
        query = null
        oldOrders.value?.also {
            oldOrders.postValue(it)
        }
    }

    val time = MutableLiveData<Time>()

    fun fetchTime() {
        viewModelScope.launch(IO) {
            getTime.getTime(token!!).collect {
                if (it is Success) {
                    it.data?.also {
                        time.postValue(it)
                    }
                }
            }
        }
    }

    fun updateTime(time: Int) {
        viewModelScope.launch(IO) {
            updateTime.updateTime(token!!, time).collect {
                Log.d("XXX", "UPDATE TIME RESP $it")
            }
        }
    }

    val loginRequestResponse = Channel<Resource<LoginResponse?>>()

    fun login(login: String, password: String) {
        viewModelScope.launch(IO) {
            this@MainViewModel.login.login(LoginRequest(login, password)).collect {
                if (it is Success) {
                    it.data?.also { data -> userPreferences.saveToken(data.token) }
                }
                loginRequestResponse.send(it)
            }
        }
    }
}