package com.example.pizzeria_admin_app.data.usecases.products.pizza

import android.util.Log
import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.product.ProductRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.product.api.models.ProductPizza
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeletePizza @Inject constructor(val productRemoteDataSource: ProductRemoteDataSource) {
    fun deletePizza(token:String,id: Int): Flow<Resource<GenericResponse?>> = flow {
        emit(Resource.Loading())
        val response =
            safeApiCall(Dispatchers.IO) { productRemoteDataSource.deletePizza(token,id) }
        emit(response)
    }
}