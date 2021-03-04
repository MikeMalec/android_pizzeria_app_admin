package com.example.pizzeria_admin_app.data.local.product

import com.example.pizzeria_admin_app.data.remote.product.api.models.Beverage

data class AlcoholGroup(val groupName: String, val alcohols: List<Beverage>)