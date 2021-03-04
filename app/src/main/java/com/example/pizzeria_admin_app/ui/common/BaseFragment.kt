package com.example.pizzeria_admin_app.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.pizzeria_admin_app.ui.MainActivity
import com.example.pizzeria_admin_app.ui.products.beverage.BeverageViewModel
import com.example.pizzeria_admin_app.ui.products.burger.BurgerViewModel
import com.example.pizzeria_admin_app.ui.products.pasta.PastaViewModel
import com.example.pizzeria_admin_app.ui.products.pita.PitaViewModel
import com.example.pizzeria_admin_app.ui.products.pizza.PizzaViewModel
import com.example.pizzeria_admin_app.ui.products.salad.SaladViewModel

open class BaseFragment(layout: Int) : Fragment(layout) {

    protected lateinit var mainActivity: MainActivity

    protected lateinit var mainViewModel: MainViewModel
    protected lateinit var pizzaViewModel: PizzaViewModel
    protected lateinit var burgerViewModel: BurgerViewModel
    protected lateinit var pitaViewModel: PitaViewModel
    protected lateinit var pastaViewModel: PastaViewModel
    protected lateinit var saladViewModel: SaladViewModel
    protected lateinit var beverageViewModel: BeverageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = (requireActivity() as MainActivity)
        mainViewModel = mainActivity.mainViewModel
        pizzaViewModel = mainActivity.pizzaViewModel
        burgerViewModel = mainActivity.burgerViewModel
        pitaViewModel = mainActivity.pitaViewModel
        saladViewModel = mainActivity.saladViewModel
        pastaViewModel = mainActivity.pastaViewModel
        beverageViewModel = mainActivity.beverageViewModel
    }
}