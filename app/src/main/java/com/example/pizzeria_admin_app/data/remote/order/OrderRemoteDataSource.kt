package com.example.pizzeria_admin_app.data.remote.order

import com.example.pizzeria_admin_app.data.remote.order.api.OrderApi
import com.example.pizzeria_admin_app.data.remote.time.Time
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRemoteDataSource @Inject constructor(val orderApi: OrderApi) {
    suspend fun getNewOrders(token: String) = orderApi.getNewOrders(token)

    suspend fun getOldOrders(token: String, lastFetched: String?) =
        orderApi.getOldOrders(token, lastFetched)

    suspend fun getFilteredOldOrders(token: String, query: String, lastFetched: String?) =
        orderApi.getFilteredOldOrders(token, query, lastFetched)

    suspend fun setOrderStatus(token: String, id: Int, status: String) =
        orderApi.setOrderStatus(token, id, status)

    suspend fun getOrderTime(token: String) = orderApi.getOrderTime(token)
    suspend fun setOrderTime(token: String, time: Time) = orderApi.setOrderTime(token, time)
}