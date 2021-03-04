package com.example.pizzeria_admin_app.data.usecases.products.burger

import android.util.Log
import com.example.pizzeria_admin_app.data.remote.product.ProductRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.product.api.models.Burger
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBurgers @Inject constructor(val productRemoteDataSource: ProductRemoteDataSource) {
    fun getBurgers(token:String,): Flow<Resource<List<Burger>?>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall(Dispatchers.IO) { productRemoteDataSource.getBurgers(token) }
        emit(response)
    }
}