package com.example.pizzeria_admin_app.utils.internet

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.N)
@Singleton
class NetworkManager @Inject constructor(
    val connectivityManager: ConnectivityManager
) :
    ConnectivityManager.NetworkCallback() {
    val isNetworkAvailable = MutableStateFlow(true)
    var didMissInternet = false

    init {
        setCallback()
    }

    private fun setCallback() {
        connectivityManager.registerDefaultNetworkCallback(this)
    }

    fun checkNetwork() {
        var isConnected = false
        connectivityManager.allNetworks.forEach { network ->
            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            networkCapability?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isConnected = true
                    return@forEach
                }
            }
        }
        isNetworkAvailable.value = isConnected
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.value = false
    }
}