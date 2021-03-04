package com.example.pizzeria_admin_app.ui.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.ProductCategory
import com.example.pizzeria_admin_app.databinding.ProductsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.products_fragment) {
    private lateinit var binding: ProductsFragmentBinding

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProductsFragmentBinding.bind(view)
        setRv()
    }

    private fun setRv() {
        categoryAdapter.itemCallback = ::showCategoryItems
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryAdapter
        }
    }

    private fun showCategoryItems(category: ProductCategory) {
        when (category.name) {
            "Pizze" -> {
                ProductsFragmentDirections.actionProductsFragmentToPizzaFragment().also {
                    findNavController().navigate(it)
                }
            }
            "Burgery" -> {
                ProductsFragmentDirections.actionProductsFragmentToBurgerFragment().also {
                    findNavController().navigate(it)
                }
            }
            "Pity" -> {
                ProductsFragmentDirections.actionProductsFragmentToPitaFragment().also {
                    findNavController().navigate(it)
                }
            }
            "Makarony" -> {
                ProductsFragmentDirections.actionProductsFragmentToPastaFragment().also {
                    findNavController().navigate(it)
                }
            }
            "Sałatki" -> {
                ProductsFragmentDirections.actionProductsFragmentToSaladFragment().also {
                    findNavController().navigate(it)
                }
            }
            "Napoje" -> {
                ProductsFragmentDirections.actionProductsFragmentToBeverageFragment().also {
                    findNavController().navigate(it)
                }
            }
        }
    }
}