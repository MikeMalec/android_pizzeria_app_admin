package com.example.pizzeria_admin_app.data.remote.order.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderInfo(
    val id: Int,
    var status: String,
    val street: String,
    val houseNumber: String,
    val city: String,
    val phoneNumber: String,
    val price: Float?,
    @SerializedName("additionalUserInfo")
    val additionalInfo: String,
    @SerializedName("payment_method")
    val paymentMethod: String,
    val createdAt: String,
    val updatedAt: String
) : Parcelable