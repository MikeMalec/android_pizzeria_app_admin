package com.example.pizzeria_admin_app.data.usecases.products.salad

import com.example.pizzeria_admin_app.data.remote.product.ProductRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.product.api.models.Salad
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.Resource.Loading
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSalads @Inject constructor(val productRemoteDataSource: ProductRemoteDataSource) {
    fun getSalads(token: String): Flow<Resource<List<Salad>?>> = flow {
        emit(Loading())
        val response = safeApiCall(IO) { productRemoteDataSource.getSalads(token) }
        emit(response)
    }
}