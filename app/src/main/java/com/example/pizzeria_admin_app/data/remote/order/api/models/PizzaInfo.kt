package com.example.pizzeria_admin_app.data.remote.order.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PizzaInfo(
    val pizza: Pizza,
    @SerializedName("pizza_size")
    val pizzaSize: Int,
    val amount: Int,
    val addons: List<Addon>,
    val sauce: Sauce?,
    @SerializedName("sauce_size")
    val sauceSize: String?,
    val breadsticks: Boolean,
) : Parcelable