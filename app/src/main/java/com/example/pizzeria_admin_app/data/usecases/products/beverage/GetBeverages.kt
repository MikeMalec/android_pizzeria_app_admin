package com.example.pizzeria_admin_app.data.usecases.products.beverage

import com.example.pizzeria_admin_app.data.remote.product.ProductRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.product.api.models.Beverage
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.Resource.Loading
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBeverages @Inject constructor(val productRemoteDataSource: ProductRemoteDataSource) {
    fun getBeverages(token:String,): Flow<Resource<List<Beverage>?>> = flow {
        emit(Loading())
        val response = safeApiCall(IO) { productRemoteDataSource.getBeverages(token) }
        emit(response)
    }
}