package com.example.pizzeria_admin_app.data.usecases.products.beverage

import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.product.ProductRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.product.api.models.Beverage
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateBeverage @Inject constructor(val productRemoteDataSource: ProductRemoteDataSource) {
    fun createBeverage(token:String,beverage: Beverage): Flow<Resource<GenericResponse?>> = flow {
        emit(Resource.Loading())
        val response =
            safeApiCall(Dispatchers.IO) { productRemoteDataSource.createBeverage(token,beverage) }
        emit(response)
    }
}