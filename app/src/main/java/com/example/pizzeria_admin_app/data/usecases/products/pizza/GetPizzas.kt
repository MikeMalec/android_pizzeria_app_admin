package com.example.pizzeria_admin_app.data.usecases.products.pizza

import com.example.pizzeria_admin_app.data.remote.product.ProductRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.product.api.models.ProductPizza
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.Resource.Loading
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPizzas @Inject constructor(val productRemoteDataSource: ProductRemoteDataSource) {
    fun getAllPizzas(token:String,): Flow<Resource<List<ProductPizza>?>> = flow {
        emit(Loading())
        val response = safeApiCall(IO) { productRemoteDataSource.getPizzas(token) }
        emit(response)
    }
}