package com.example.pizzeria_admin_app.data.usecases.time

import com.example.pizzeria_admin_app.data.remote.order.OrderRemoteDataSource
import com.example.pizzeria_admin_app.data.remote.time.Time
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.data.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateTime @Inject constructor(val orderRemoteDataSource: OrderRemoteDataSource) {
    fun updateTime(token: String, time: Int): Flow<Resource<Time?>> = flow {
        emit(Resource.Loading())
        val response =
            safeApiCall(Dispatchers.IO) { orderRemoteDataSource.setOrderTime(token, Time(time)) }
        emit(response)
    }
}