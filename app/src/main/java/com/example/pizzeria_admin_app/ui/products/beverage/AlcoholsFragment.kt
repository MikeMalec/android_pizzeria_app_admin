package com.example.pizzeria_admin_app.ui.products.beverage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.api.models.Beverage
import com.example.pizzeria_admin_app.databinding.AlcoholsFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlcoholsFragment() :
    BaseFragment(R.layout.alcohols_fragment), NamedFragment {

    private lateinit var binding: AlcoholsFragmentBinding

    lateinit var updateCallback: (beverage: Beverage) -> Unit

    @Inject
    lateinit var alcoholsAdapter: AlcoholsAdapter

    override fun getName(): String {
        return "Alkohole"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AlcoholsFragmentBinding.bind(view)
        setRv()
        observeAlcohols()
    }

    private fun setRv() {
        alcoholsAdapter.itemClick = updateCallback
        binding.rvAlcohols.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = alcoholsAdapter
        }
    }

    private fun observeAlcohols() {
        lifecycleScope.launchWhenStarted {
            beverageViewModel.alcoholGroups.observe(viewLifecycleOwner, Observer {
                alcoholsAdapter.items.clear()
                alcoholsAdapter.items.addAll(it)
                alcoholsAdapter.notifyDataSetChanged()
            })
        }
    }
}