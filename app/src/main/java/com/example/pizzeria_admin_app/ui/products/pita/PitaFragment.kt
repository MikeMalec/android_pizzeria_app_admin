package com.example.pizzeria_admin_app.ui.products.pita

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.Product
import com.example.pizzeria_admin_app.data.remote.product.api.models.Pita
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.databinding.PitaFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.example.pizzeria_admin_app.ui.products.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PitaFragment : BaseFragment(R.layout.pita_fragment) {
    private lateinit var binding: PitaFragmentBinding

    @Inject
    lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pitaViewModel.fetchPitas()
        binding = PitaFragmentBinding.bind(view)
        binding.fabCreatePita.setOnClickListener { createPita() }
        setRv()
        observePitas()
    }

    private fun setRv() {
        productAdapter.itemCallback = ::updatePita
        binding.rvPitas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }

    private fun observePitas() {
        lifecycleScope.launchWhenStarted {
            pitaViewModel.pitas.observe(viewLifecycleOwner, Observer {
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

    private fun updatePita(pita: Product) {
        pitaViewModel.clearCurrentPitaInfo()
        PitaFragmentDirections.actionPitaFragmentToPitaUpdateCreateFragment(pita as Pita).also {
            findNavController().navigate(it)
        }
    }

    private fun createPita() {
        pitaViewModel.clearCurrentPitaInfo()
        PitaFragmentDirections.actionPitaFragmentToPitaUpdateCreateFragment(null).also {
            findNavController().navigate(it)
        }
    }
}