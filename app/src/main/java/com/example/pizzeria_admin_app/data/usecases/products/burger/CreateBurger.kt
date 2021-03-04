package com.example.pizzeria_admin_app.data.usecases.products.burger

import com.example.pizzeria_admin_app.data.remote.order.api.models.GenericResponse
import com.example.pizzeria_admin_app.data.remote.product.ProductRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.product.api.models.Burger
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.Resource.Loading
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateBurger @Inject constructor(val productRemoteDataSource: ProductRemoteDataSource) {
    fun createBurger(token:String,burger: Burger): Flow<Resource<GenericResponse?>> = flow {
        emit(Loading())
        val response = safeApiCall(IO) { productRemoteDataSource.createBurger(token,burger) }
        emit(response)
    }
}