package com.example.pizzeria_admin_app.ui.products.salad

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.Product
import com.example.pizzeria_admin_app.data.remote.product.api.models.Salad
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.databinding.SaladFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.example.pizzeria_admin_app.ui.products.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SaladFragment : BaseFragment(R.layout.salad_fragment) {
    private lateinit var binding: SaladFragmentBinding

    @Inject
    lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saladViewModel.fetchSalads()
        binding = SaladFragmentBinding.bind(view)
        setRv()
        observeSalads()
        binding.fabCreateSalad.setOnClickListener { createSalad() }
    }

    private fun setRv() {
        productAdapter.itemCallback = ::updateSalad
        binding.rvSalads.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }

    private fun observeSalads() {
        lifecycleScope.launchWhenStarted {
            saladViewModel.salad.observe(viewLifecycleOwner, Observer {
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

    private fun updateSalad(salad: Product) {
        saladViewModel.clearCurrentSaladInfo()
        SaladFragmentDirections.actionSaladFragmentToCreateUpdateSaladFragment(salad as Salad)
            .also {
                findNavController().navigate(it)
            }
    }

    private fun createSalad() {
        saladViewModel.clearCurrentSaladInfo()
        SaladFragmentDirections.actionSaladFragmentToCreateUpdateSaladFragment(null).also {
            findNavController().navigate(it)
        }
    }
}