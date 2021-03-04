package com.example.pizzeria_admin_app.data.remote.order.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val orderInfo: OrderInfo,
    val pizzas: List<PizzaInfo>,
    val burgers: List<FoodInfo>,
    val pastas: List<FoodInfo>,
    val pitas: List<FoodInfo>,
    val salads: List<FoodInfo>,
    val beverages: List<FoodInfo>
) : Parcelable {
    fun getDescription(): String {
        var desc = ""
        pizzas.forEach {
            desc += "\n ${if (!it.pizza.name.contains("pizza")) "Pizza" else ""} ${it.pizza.name} rozmiar: ${it.pizzaSize} ilość: ${it.amount}"
            if (it.sauce != null) {
                when (it.sauceSize) {
                    "small" -> desc += "\n Sos: ${it.sauce.name} rozmiar: mały"
                    "big" -> desc += "\n Sos: ${it.sauce.name} rozmiar: duży"
                }
            }
            if (it.addons.isNotEmpty()) {
                desc += "\n Dodatki:"
                it.addons.forEach { addon ->
                    desc += "\n ${addon.name}"
                }
            }
        }
        burgers.forEach {
            when (it.size) {
                "small" -> desc += "\n ${it.name} ilość: ${it.amount}"
                "big" -> desc += "\n ${it.name} w zestawie ilość: ${it.amount}"
            }
        }
        pastas.forEach {
            when (it.size) {
                "small" -> desc += "\n Makaron ${it.name} mały ilość: ${it.amount}"
                "big" -> desc += "\n Makaron ${it.name} duży ilość: ${it.amount}"
            }
        }
        pitas.forEach {
            when (it.size) {
                "small" -> desc += "\n Pita ${it.name} mała ilość: ${it.amount}"
                "big" -> desc += "\n Pita ${it.name} duża ilość: ${it.amount}"
            }
        }
        salads.forEach {
            desc += "\n Sałatka ${it.name} ilość: ${it.amount}"
        }
        beverages.forEach {
            desc += "\n ${it.name} rozmiar: ${it.size} ilość: ${it.amount}"
        }
        return desc
    }
}