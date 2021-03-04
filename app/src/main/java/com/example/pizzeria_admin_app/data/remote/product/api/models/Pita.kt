package com.example.pizzeria_admin_app.data.remote.product.api.models

import android.os.Parcelable
import com.example.pizzeria_admin_app.data.remote.product.Product
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pita(
    var id: Int?,
    var number: Int,
    var name: String,
    var ingredients: List<String>,
    @SerializedName(value = "small_price")
    var smallPrice: Float,
    @SerializedName(value = "big_price")
    var bigPrice: Float
) : Parcelable, Product {
    override fun getEquals(other: Any?): Boolean {
        return equals(other)
    }

    override fun getProductName(): String {
        return name
    }
}