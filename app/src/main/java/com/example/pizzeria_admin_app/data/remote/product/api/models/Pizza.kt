package com.example.pizzeria_admin_app.data.remote.product.api.models

import android.os.Parcelable
import com.example.pizzeria_admin_app.data.remote.product.Product
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pizza(
    var id: Int?,
    var number: Int,
    var name: String,
    var ingredients: List<String>,
    @SerializedName(value = "size_24_price")
    var size24Price: Float,
    @SerializedName(value = "size_30_price")
    var size30Price: Float,
    @SerializedName(value = "size_40_price")
    var size40Price: Float,
    @SerializedName(value = "size_50_price")
    var size50Price: Float
) : Parcelable, Product {
    override fun getProductName(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun getEquals(other: Any?): Boolean {
        return equals(other)
    }
}

typealias ProductPizza = Pizza