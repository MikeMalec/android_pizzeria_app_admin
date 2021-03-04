package com.example.pizzeria_admin_app.data.remote.order.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pizzeria_admin_app.utils.constants.NOTIFICATION_CHANNEL_ID
import com.example.pizzeria_admin_app.utils.constants.NOTIFICATION_CHANNEL_NAME
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelManager @Inject constructor(val notificationManager: NotificationManager) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        val channel =
            NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }
}