package com.example.pizzeria_admin_app.data.usecases.order

import com.example.pizzeria_admin_app.data.remote.order.OrderRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.order.api.models.OrdersResponse
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.Resource.Loading
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFilteredOldOrders @Inject constructor(val orderRemoteDataSource: OrderRemoteDataSource) {
    fun getFilteredOldOrders(
        token: String,
        query: String,
        lastFetched: String?
    ): Flow<Resource<OrdersResponse?>> =
        flow {
            emit(Loading())
            val response =
                safeApiCall(IO) {
                    orderRemoteDataSource.getFilteredOldOrders(
                        token,
                        query,
                        lastFetched
                    )
                }
            emit(response)
        }
}