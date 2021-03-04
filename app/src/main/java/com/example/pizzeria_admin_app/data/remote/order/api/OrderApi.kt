package com.example.pizzeria_admin_app.data.remote.order.api

import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.order.api.models.Order
import com.example.pizzeria_admin_app.data.remote.order.api.models.OrdersResponse
import com.example.pizzeria_admin_app.data.remote.time.Time
import retrofit2.http.*

interface OrderApi {
    @GET("orders")
    suspend fun getNewOrders(
        @Header("Authorization") token: String,
        @Query("type") type: String = "new"
    ): OrdersResponse

    @PUT("orders/{id}")
    suspend fun setOrderStatus(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Query("status") status: String
    ): GenericResponse

    @GET("orders")
    suspend fun getOldOrders(
        @Header("Authorization") token: String,
        @Query("lastFetched") date: String?,
        @Query("type") type: String = "old",
    ): OrdersResponse

    @GET("orders")
    suspend fun getFilteredOldOrders(
        @Header("Authorization") token: String,
        @Query("query") query: String,
        @Query("lastFetched") date: String?,
    ): OrdersResponse

    @GET("time")
    suspend fun getOrderTime(@Header("Authorization") token: String): Time

    @POST("time")
    suspend fun setOrderTime(@Header("Authorization") token: String, @Body time: Time): Time
}