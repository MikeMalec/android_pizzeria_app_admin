package com.example.pizzeria_admin_app.di

import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.net.ConnectivityManager
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.local.preferences.UserPreferences
import com.example.pizzeria_admin_app.data.remote.auth.AuthApi
import com.example.pizzeria_admin_app.data.remote.order.api.OrderApi
import com.example.pizzeria_admin_app.data.remote.product.api.ProductApi
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.utils.auth.TokenValidator
import com.example.pizzeria_admin_app.utils.constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideOrderApi(retrofit: Retrofit.Builder): OrderApi {
        return retrofit.build().create(OrderApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit.Builder): ProductApi {
        return retrofit.build().create(ProductApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit.Builder): AuthApi {
        return retrofit.build().create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    fun provideMediaPlayer(@ApplicationContext context: Context): MediaPlayer {
        return MediaPlayer.create(context, R.raw.cash_sound)
    }

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}