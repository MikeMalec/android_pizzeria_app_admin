package com.example.pizzeria_admin_app.data.usecases.products.pasta

import com.example.pizzeria_admin_app.data.remote.product.ProductRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.product.api.models.Pasta
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.Resource.Loading
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPastas @Inject constructor(val productRemoteDataSource: ProductRemoteDataSource) {
    fun getPastas(token:String,): Flow<Resource<List<Pasta>?>> = flow {
        emit(Loading())
        val response = safeApiCall(IO) { productRemoteDataSource.getPastas(token) }
        emit(response)
    }
}