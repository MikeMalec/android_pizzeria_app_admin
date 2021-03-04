package com.example.pizzeria_admin_app.ui.products.burger

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.Product
import com.example.pizzeria_admin_app.data.remote.product.api.models.Burger
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.databinding.BurgerFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.example.pizzeria_admin_app.ui.products.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BurgerFragment : BaseFragment(R.layout.burger_fragment) {
    private lateinit var binding: BurgerFragmentBinding

    @Inject
    lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        burgerViewModel.fetchBurgers()
        binding = BurgerFragmentBinding.bind(view)
        binding.fabCreateBurger.setOnClickListener { createBurger() }
        setRv()
        observeBurgers()
    }

    private fun setRv() {
        productAdapter.itemCallback = ::updateBurger
        binding.rvBurgers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }

    private fun observeBurgers() {
        lifecycleScope.launchWhenStarted {
            burgerViewModel.burger.observe(viewLifecycleOwner, Observer {
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

    private fun createBurger() {
        burgerViewModel.clearCurrentBurgerInfo()
        BurgerFragmentDirections.actionBurgerFragmentToBurgerUpdateCreateFragment(null).also {
            findNavController().navigate(it)
        }
    }

    private fun updateBurger(burger: Product) {
        burgerViewModel.clearCurrentBurgerInfo()
        BurgerFragmentDirections.actionBurgerFragmentToBurgerUpdateCreateFragment(burger as Burger)
            .also {
                findNavController().navigate(it)
            }
    }
}