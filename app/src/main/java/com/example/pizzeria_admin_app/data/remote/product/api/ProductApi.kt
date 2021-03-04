package com.example.pizzeria_admin_app.data.remote.product.api

import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.product.api.models.*
import retrofit2.http.*

interface ProductApi {
    @GET("products/pizzas")
    suspend fun getPizzas(@Header("Authorization") token: String): List<ProductPizza>

    @POST("products/pizzas")
    suspend fun createPizza(
        @Header("Authorization") token: String,
        @Body pizza: ProductPizza
    ): GenericResponse

    @PUT("products/pizzas/{id}")
    suspend fun updatePizza(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body pizza: ProductPizza
    ): GenericResponse

    @DELETE("products/pizzas/{id}")
    suspend fun deletePizza(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): GenericResponse

    @GET("products/burgers")
    suspend fun getBurgers(@Header("Authorization") token: String): List<Burger>

    @POST("products/burgers")
    suspend fun createBurger(
        @Header("Authorization") token: String,
        @Body burger: Burger
    ): GenericResponse

    @PUT("products/burgers/{id}")
    suspend fun updateBurger(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body burger: Burger
    ): GenericResponse

    @DELETE("products/burgers/{id}")
    suspend fun deleteBurger(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): GenericResponse

    @GET("products/pitas")
    suspend fun getPitas(@Header("Authorization") token: String): List<Pita>

    @POST("products/pitas")
    suspend fun createPita(
        @Header("Authorization") token: String,
        @Body pita: Pita
    ): GenericResponse

    @PUT("products/pitas/{id}")
    suspend fun updatePita(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body pita: Pita
    ): GenericResponse

    @DELETE("products/pitas/{id}")
    suspend fun deletePita(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): GenericResponse

    @GET("products/pastas")
    suspend fun getPastas(@Header("Authorization") token: String): List<Pasta>

    @POST("products/pastas")
    suspend fun createPasta(
        @Header("Authorization") token: String,
        @Body pasta: Pasta
    ): GenericResponse

    @PUT("products/pastas/{id}")
    suspend fun updatePasta(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body pasta: Pasta
    ): GenericResponse

    @DELETE("products/pastas/{id}")
    suspend fun deletePasta(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): GenericResponse

    @GET("products/salads")
    suspend fun getSalads(@Header("Authorization") token: String): List<Salad>

    @POST("products/salads")
    suspend fun createSalad(
        @Header("Authorization") token: String,
        @Body salad: Salad
    ): GenericResponse

    @PUT("products/salads/{id}")
    suspend fun updateSalad(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body salad: Salad
    ): GenericResponse

    @DELETE("products/salads/{id}")
    suspend fun deleteSalad(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): GenericResponse

    @GET("products/beverages")
    suspend fun getBeverages(@Header("Authorization") token: String): List<Beverage>

    @POST("products/beverages")
    suspend fun createBeverage(
        @Header("Authorization") token: String,
        @Body beverage: Beverage
    ): GenericResponse

    @PUT("products/beverages/{id}")
    suspend fun updateBeverage(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body beverage: Beverage
    ): GenericResponse

    @DELETE("products/beverages/{id}")
    suspend fun deleteBeverage(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): GenericResponse
}

