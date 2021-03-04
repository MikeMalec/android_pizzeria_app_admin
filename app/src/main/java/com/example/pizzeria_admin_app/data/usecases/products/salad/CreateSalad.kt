package com.example.pizzeria_admin_app.data.usecases.products.salad

import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.product.ProductRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.product.api.models.Salad
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.Resource.Loading
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateSalad @Inject constructor(val productRemoteDataSource: ProductRemoteDataSource) {
    fun createSalad(token: String, salad: Salad): Flow<Resource<GenericResponse?>> = flow {
        emit(Loading())
        val response =
            safeApiCall(Dispatchers.IO) { productRemoteDataSource.createSalad(token, salad) }
        emit(response)
    }
}