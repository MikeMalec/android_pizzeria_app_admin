package com.example.pizzeria_admin_app.data.usecases.time

import com.example.pizzeria_admin_app.data.remote.order.OrderRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.time.Time
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.Resource.Loading
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTime @Inject constructor(val orderRemoteDataSource: OrderRemoteDataSource) {
    fun getTime(token:String,): Flow<Resource<Time?>> = flow {
        emit(Loading())
        val response = safeApiCall(IO) { orderRemoteDataSource.getOrderTime(token) }
        emit(response)
    }
}