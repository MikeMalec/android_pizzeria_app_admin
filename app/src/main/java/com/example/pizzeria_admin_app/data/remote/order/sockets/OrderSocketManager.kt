package com.example.pizzeria_admin_app.data.remote.order.sockets

import android.util.Log
import com.example.pizzeria_admin_app.data.remote.order.api.models.Order
import com.example.pizzeria_admin_app.utils.constants.BASE_URL
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import io.socket.emitter.Emitter
import io.socket.engineio.client.Transport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderSocketManager @Inject constructor(val gson: Gson) {
    private lateinit var socket: Socket
    private var job = Job()

    private val socketData = Channel<Array<out Any?>>(Channel.BUFFERED)

    lateinit var orders: Channel<Order>

    init {
        observeSocketData()
    }

    private fun observeSocketData() {
        CoroutineScope(Dispatchers.IO + job).launch {
            socketData.consumeAsFlow().collect {
                dispatchNewOrder(it)
            }
        }
    }

    private suspend fun dispatchNewOrder(args: Array<out Any?>?) {
        args?.also {
            val order = gson.fromJson(it[0].toString(), Order::class.java)
            orders.send(order)
        }
    }

    fun setOrdersChannel() {
        orders = Channel(Channel.BUFFERED)
    }

    fun connect(token: String) {
        socket = IO.socket(BASE_URL)
        socket.connect()
        Log.d("XXX", "TOKEN = $token")
        socket.emit("JOIN_ORDERS", token)
        socket.on("NEW_ORDER") { args: Array<out Any?> ->
            Log.d("XXX", "NEW ORDER")
            CoroutineScope(Dispatchers.IO + job).launch {
                socketData.send(args)
            }
        }
    }

    fun restartSocket() {
        socket.connect()
        socket.emit("JOIN_ORDERS")
    }

    fun disconnect() {
        if (::socket.isInitialized) {
            socket.disconnect()
        }
    }

    private fun setErrorListener() {
        socket.io().on(Manager.EVENT_TRANSPORT, Emitter.Listener { args ->
            val transport: Transport = args[0] as Transport
            transport.on(Transport.EVENT_ERROR, Emitter.Listener { args ->
                val e = args[0] as Exception
                Log.e("XXX", "Transport error $e")
                e.printStackTrace()
                e.cause!!.printStackTrace()
            })
        })
    }
}