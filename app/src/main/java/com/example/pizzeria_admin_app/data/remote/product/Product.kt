package com.example.pizzeria_admin_app.data.remote.product

interface Product {
    fun getProductName(): String
    fun getEquals(other:Any?): Boolean
}