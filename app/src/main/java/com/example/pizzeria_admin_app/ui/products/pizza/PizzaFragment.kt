package com.example.pizzeria_admin_app.ui.products.pizza

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.Product
import com.example.pizzeria_admin_app.data.remote.product.api.models.Pizza
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.databinding.PizzaFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.example.pizzeria_admin_app.ui.products.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PizzaFragment : BaseFragment(R.layout.pizza_fragment) {
    private lateinit var binding: PizzaFragmentBinding

    @Inject
    lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PizzaFragmentBinding.bind(view)
        setRv()
        observePizza()
        binding.fabCreatePizza.setOnClickListener { createPizza() }
    }

    override fun onStart() {
        super.onStart()
        pizzaViewModel.fetchPizza()
    }

    private fun setRv() {
        productAdapter.itemCallback = ::updatePizza
        binding.rvPizza.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }

    private fun observePizza() {
        lifecycleScope.launchWhenStarted {
            pizzaViewModel.pizza.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        it.data?.also { items -> productAdapter.submitList(items) }
                        hideLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()
                        it.error?.also { err -> mainActivity.shortMessage(err) }
                    }
                }
            })
        }
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun updatePizza(product: Product) {
        pizzaViewModel.clearCurrentPizzaInfo()
        PizzaFragmentDirections.actionPizzaFragmentToPizzaUpdateCreateFragment(product as Pizza)
            .also {
                findNavController().navigate(it)
            }
    }

    private fun createPizza() {
        pizzaViewModel.clearCurrentPizzaInfo()
        PizzaFragmentDirections.actionPizzaFragmentToPizzaUpdateCreateFragment(null)
            .also {
                findNavController().navigate(it)
            }
    }
}