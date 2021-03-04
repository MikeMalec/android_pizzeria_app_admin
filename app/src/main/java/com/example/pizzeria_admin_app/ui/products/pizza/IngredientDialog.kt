package com.example.pizzeria_admin_app.ui.products.pizza

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.pizzeria_admin_app.databinding.CreateIngredientBinding
import com.example.pizzeria_admin_app.ui.MainActivity
import com.example.pizzeria_admin_app.ui.products.burger.BurgerViewModel
import com.example.pizzeria_admin_app.ui.products.pasta.PastaViewModel
import com.example.pizzeria_admin_app.ui.products.pita.PitaViewModel
import com.example.pizzeria_admin_app.ui.products.salad.SaladViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngredientDialog : BottomSheetDialogFragment() {
    private lateinit var binding: CreateIngredientBinding

    private val args: IngredientDialogArgs by navArgs()
    private val foodType: String
        get() = args.foodType

    private lateinit var pizzaViewModel: PizzaViewModel
    private lateinit var burgerViewModel: BurgerViewModel
    private lateinit var pitaViewModel: PitaViewModel
    private lateinit var pastaViewModel: PastaViewModel
    private lateinit var saladViewModel: SaladViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateIngredientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pizzaViewModel = (requireActivity() as MainActivity).pizzaViewModel
        burgerViewModel = (requireActivity() as MainActivity).burgerViewModel
        pitaViewModel = (requireActivity() as MainActivity).pitaViewModel
        pastaViewModel = (requireActivity() as MainActivity).pastaViewModel
        saladViewModel = (requireActivity() as MainActivity).saladViewModel
        binding.btnCancelIngredientDialog.setOnClickListener { dismiss() }
        binding.btnAddIngredient.setOnClickListener {
            if (binding.etIngredientName.text.toString().isNotEmpty()) {
                when (foodType) {
                    "Pizza" -> {
                        pizzaViewModel.currentPizzaIngredients.value = listOf(
                            *pizzaViewModel.currentPizzaIngredients.value!!.toTypedArray(),
                            binding.etIngredientName.text.toString()
                        )
                    }
                    "Burger" -> {
                        burgerViewModel.currentBurgerIngredients.value = listOf(
                            *burgerViewModel.currentBurgerIngredients.value!!.toTypedArray(),
                            binding.etIngredientName.text.toString()
                        )
                    }
                    "Pita" -> {
                        pitaViewModel.currentPitaIngredients.value = listOf(
                            *pitaViewModel.currentPitaIngredients.value!!.toTypedArray(),
                            binding.etIngredientName.text.toString()
                        )
                    }
                    "Pasta" -> {
                        pastaViewModel.currentPastaIngredients.value = listOf(
                            *pastaViewModel.currentPastaIngredients.value!!.toTypedArray(),
                            binding.etIngredientName.text.toString()
                        )
                    }
                    "Salad" -> {
                        saladViewModel.currentSaladIngredients.value = listOf(
                            *saladViewModel.currentSaladIngredients.value!!.toTypedArray(),
                            binding.etIngredientName.text.toString()
                        )
                    }
                }
                dismiss()
            }
        }
    }
}