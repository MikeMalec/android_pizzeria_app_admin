package com.example.pizzeria_admin_app.data.usecases.products.pita

import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.product.ProductRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.product.api.models.Pita
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreatePita @Inject constructor(val productRemoteDataSource: ProductRemoteDataSource) {
    fun createPita(token: String, pita: Pita): Flow<Resource<GenericResponse?>> = flow {
        emit(Resource.Loading())
        val response =
            safeApiCall(Dispatchers.IO) { productRemoteDataSource.createPita(token, pita) }
        emit(response)
    }
}