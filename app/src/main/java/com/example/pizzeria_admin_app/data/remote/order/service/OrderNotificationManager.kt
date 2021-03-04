package com.example.pizzeria_admin_app.data.remote.order.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.ui.MainActivity
import com.example.pizzeria_admin_app.utils.constants.NOTIFICATION_CHANNEL_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderNotificationManager @Inject constructor(@ApplicationContext val context: Context) {
    fun getNotification(): Notification {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_add_shopping_cart_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .setOngoing(true)
            .setContentTitle("Oczekuje na nowe zamówienia")
            .setContentIntent(getPendingIntent()).build()
    }

    private fun getPendingIntent(): PendingIntent {
        return NavDeepLinkBuilder(context).setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.navigation_graph).setDestination(R.id.newOrdersFragment)
            .createPendingIntent()
    }
}