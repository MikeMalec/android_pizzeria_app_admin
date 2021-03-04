package com.example.pizzeria_admin_app.data.remote.product.api.models

import android.os.Parcelable
import com.example.pizzeria_admin_app.data.remote.product.Product
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Burger(
    var id: Int?,
    var number: Int,
    var name: String,
    var ingredients: List<String>,
    @SerializedName(value = "solo_price")
    var soloPrice: Float,
    @SerializedName(value = "set_price")
    var setPrice: Float
) : Parcelable, Product {
    override fun getProductName(): String {
        return name
    }

    override fun getEquals(other: Any?): Boolean {
        return equals(other)
    }

}