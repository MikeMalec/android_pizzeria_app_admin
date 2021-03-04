package com.example.pizzeria_admin_app.data.remote.order.service

import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.pizzeria_admin_app.data.local.preferences.UserPreferences
import com.example.pizzeria_admin_app.data.remote.order.api.models.Order
import com.example.pizzeria_admin_app.data.remote.order.sockets.OrderSocketManager
import com.example.pizzeria_admin_app.data.usecases.order.GetNewOrders
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.utils.auth.TokenValidator
import com.example.pizzeria_admin_app.utils.constants.ORDER_NOTIFICATION_ID
import com.example.pizzeria_admin_app.utils.constants.RESTART_SOCKET
import com.example.pizzeria_admin_app.utils.constants.START_SERVICE
import com.example.pizzeria_admin_app.utils.constants.STOP_SERVICE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class OrderService : LifecycleService() {

    @Inject
    lateinit var channelManager: ChannelManager

    @Inject
    lateinit var orderNotificationManager: OrderNotificationManager

    @Inject
    lateinit var orderSocketManager: OrderSocketManager

    @Inject
    lateinit var mediaPlayer: MediaPlayer

    @Inject
    lateinit var getNewOrders: GetNewOrders

    @Inject
    lateinit var userPreferences: UserPreferences

    @Inject
    lateinit var tokenValidator: TokenValidator

    var token: String? = null

    companion object {
        val newOrdersIntervalData = Channel<List<Order>>(Channel.BUFFERED)
        val currentAmountOfOrders = MutableLiveData(0)
        val orders = Channel<Order>(Channel.BUFFERED)
    }

    override fun onCreate() {
        super.onCreate()
        startNewOrdersInterval()
        observeToken()
    }

    private fun observeToken() {
        lifecycleScope.launch(IO) {
            userPreferences.getToken().collect {
                token = it
            }
        }
    }

    private fun startNewOrdersInterval() {
        lifecycleScope.launch(IO) {
            while (true) {
                delay(300000)
                getNewOrders.getNewOrders(token!!).collect {
                    if (it is Resource.Error) {
                        tokenValidator.checkIfInvalidTokenFromRequestResponse(it)
                    }
                    if (it is Resource.Success) {
                        it.data?.also {
                            if (it.orders.size > currentAmountOfOrders.value!!) {
                                mediaPlayer.start()
                                newOrdersIntervalData.send(it.orders)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                START_SERVICE -> startForegroundService()
                RESTART_SOCKET -> orderSocketManager.restartSocket()
                STOP_SERVICE -> stopForegroundService()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }


    private fun startForegroundService() {
        lifecycleScope.launchWhenStarted {
            delay(1500)
            orderSocketManager.setOrdersChannel()
            orderSocketManager.connect(token!!)
            observeNewOrders()
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                channelManager.createNotificationChannel()
            }
            startForeground(ORDER_NOTIFICATION_ID, orderNotificationManager.getNotification())
        }
    }

    private fun observeNewOrders() {
        lifecycleScope.launchWhenStarted {
            orderSocketManager.orders.consumeAsFlow().collect {
                mediaPlayer.start()
                orders.send(it)
            }
        }
    }

    private fun stopForegroundService() {
        mediaPlayer.release()
        orderSocketManager.disconnect()
        stopForeground(true)
        stopSelf()
    }
}