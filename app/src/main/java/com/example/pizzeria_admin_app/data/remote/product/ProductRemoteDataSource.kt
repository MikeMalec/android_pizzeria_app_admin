package com.example.pizzeria_admin_app.data.remote.product

import com.example.pizzeria_admin_app.data.remote.product.api.ProductApi
import com.example.pizzeria_admin_app.data.remote.product.api.models.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSource @Inject constructor(val productApi: ProductApi) {
    suspend fun getPizzas(token: String) = productApi.getPizzas(token)
    suspend fun createPizza(token: String, pizza: ProductPizza) =
        productApi.createPizza(token, pizza)

    suspend fun updatePizza(token: String, id: Int, pizza: ProductPizza) =
        productApi.updatePizza(token, id, pizza)

    suspend fun deletePizza(token: String, id: Int) = productApi.deletePizza(token, id)

    suspend fun getBurgers(token: String) = productApi.getBurgers(token)
    suspend fun createBurger(token: String, burger: Burger) = productApi.createBurger(token, burger)
    suspend fun updateBurger(token: String, id: Int, burger: Burger) =
        productApi.updateBurger(token, id, burger)

    suspend fun deleteBurger(token: String, id: Int) = productApi.deleteBurger(token, id)

    suspend fun getPitas(token: String) = productApi.getPitas(token)
    suspend fun createPita(token: String, pita: Pita) = productApi.createPita(token, pita)
    suspend fun updatePita(token: String, id: Int, pita: Pita) =
        productApi.updatePita(token, id, pita)

    suspend fun deletePita(token: String, id: Int) = productApi.deletePita(token, id)

    suspend fun getPastas(token: String) = productApi.getPastas(token)
    suspend fun createPasta(token: String, pasta: Pasta) = productApi.createPasta(token, pasta)
    suspend fun updatePasta(token: String, id: Int, pasta: Pasta) =
        productApi.updatePasta(token, id, pasta)

    suspend fun deletePasta(token: String, id: Int) = productApi.deletePasta(token, id)

    suspend fun getSalads(token: String) = productApi.getSalads(token)
    suspend fun createSalad(token: String, salad: Salad) = productApi.createSalad(token, salad)
    suspend fun updateSalad(token: String, id: Int, salad: Salad) =
        productApi.updateSalad(token, id, salad)

    suspend fun deleteSalad(token: String, id: Int) = productApi.deleteSalad(token, id)

    suspend fun getBeverages(token: String) = productApi.getBeverages(token)
    suspend fun createBeverage(token: String, beverage: Beverage) =
        productApi.createBeverage(token, beverage)

    suspend fun updateBeverage(token: String, id: Int, beverage: Beverage) =
        productApi.updateBeverage(token, id, beverage)

    suspend fun deleteBeverage(token: String, id: Int) = productApi.deleteBeverage(token, id)
}

