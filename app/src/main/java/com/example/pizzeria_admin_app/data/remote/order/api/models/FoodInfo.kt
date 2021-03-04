package com.example.pizzeria_admin_app.data.remote.order.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FoodInfo(
    val name: String,
    val size: String,
    val amount: Int,
    @SerializedName("food_type")
    val foodType: String
) : Parcelable