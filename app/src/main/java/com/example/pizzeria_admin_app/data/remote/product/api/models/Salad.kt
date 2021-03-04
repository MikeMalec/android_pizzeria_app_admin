package com.example.pizzeria_admin_app.data.remote.product.api.models

import android.os.Parcelable
import com.example.pizzeria_admin_app.data.remote.product.Product
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Salad(
    var id: Int?,
    var number: Int,
    var name: String,
    var ingredients: List<String>,
    var price: Float
) : Parcelable, Product {
    override fun getEquals(other: Any?): Boolean {
        return equals(other)
    }

    override fun getProductName(): String {
        return name
    }
}