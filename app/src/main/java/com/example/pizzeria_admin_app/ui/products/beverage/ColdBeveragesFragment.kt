package com.example.pizzeria_admin_app.ui.products.beverage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.api.models.Beverage
import com.example.pizzeria_admin_app.databinding.ColdBeveragesFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ColdBeveragesFragment() :
    BaseFragment(R.layout.cold_beverages_fragment), NamedFragment {

    private lateinit var binding: ColdBeveragesFragmentBinding

    lateinit var updateCallback: (beverage: Beverage) -> Unit

    @Inject
    lateinit var beverageAdapter: BeverageAdapter

    override fun getName(): String {
        return "Zimne napoje"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ColdBeveragesFragmentBinding.bind(view)
        setRv()
        observeColdBeverages()
    }

    private fun setRv() {
        beverageAdapter.itemClick = updateCallback
        binding.rvColdBeverages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = beverageAdapter
        }
    }

    private fun observeColdBeverages() {
        lifecycleScope.launchWhenStarted {
            beverageViewModel.coldBeverages.observe(viewLifecycleOwner, Observer {
                beverageAdapter.items.clear()
                beverageAdapter.items.addAll(it)
                beverageAdapter.notifyDataSetChanged()
            })
        }
    }
}