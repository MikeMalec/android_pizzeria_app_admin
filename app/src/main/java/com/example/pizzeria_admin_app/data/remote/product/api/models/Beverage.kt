package com.example.pizzeria_admin_app.data.remote.product.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Beverage(
    var id: Int?,
    var name: String,
    var number: String? = "1",
    var type: String,
    @SerializedName(value = "alcohol_type")
    var alcoholType: String?,
    var size: String?,
    @SerializedName(value = "small_price")
    var smallPrice: Float?,
    @SerializedName(value = "big_price")
    var bigPrice: Float?,
    var price: Float?,
    var orderable: Boolean
) : Parcelable