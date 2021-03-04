package com.example.pizzeria_admin_app.ui.products.pasta

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.Product
import com.example.pizzeria_admin_app.data.remote.product.api.models.Pasta
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.databinding.PastaFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.example.pizzeria_admin_app.ui.products.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PastaFragment : BaseFragment(R.layout.pasta_fragment) {
    private lateinit var binding: PastaFragmentBinding

    @Inject
    lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pastaViewModel.fetchPastas()
        binding = PastaFragmentBinding.bind(view)
        binding.fabCreatePasta.setOnClickListener { createPasta() }
        setRv()
        observePastas()
    }

    private fun setRv() {
        productAdapter.itemCallback = ::updatePasta
        binding.rvPasta.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }
    private fun observePastas() {
        lifecycleScope.launchWhenStarted {
            pastaViewModel.pasta.observe(viewLifecycleOwner, Observer {
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

    private fun updatePasta(pasta: Product) {
        pastaViewModel.clearCurrentPastaInfo()
        PastaFragmentDirections.actionPastaFragmentToPastaUpdateCreateFragment(pasta as Pasta).also {
            findNavController().navigate(it)
        }
    }

    private fun createPasta() {
        pastaViewModel.clearCurrentPastaInfo()
        PastaFragmentDirections.actionPastaFragmentToPastaUpdateCreateFragment(null).also {
            findNavController().navigate(it)
        }
    }
}