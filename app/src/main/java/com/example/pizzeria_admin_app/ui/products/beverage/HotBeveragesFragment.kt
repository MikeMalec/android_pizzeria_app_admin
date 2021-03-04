package com.example.pizzeria_admin_app.ui.products.beverage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.api.models.Beverage
import com.example.pizzeria_admin_app.databinding.HotBeveragesFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HotBeveragesFragment() :
    BaseFragment(R.layout.hot_beverages_fragment), NamedFragment {

    private lateinit var binding: HotBeveragesFragmentBinding

    lateinit var updateCallback: (beverage: Beverage) -> Unit

    @Inject
    lateinit var beveragesAdapter: BeverageAdapter

    override fun getName(): String {
        return "Ciepłe napoje"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HotBeveragesFragmentBinding.bind(view)
        setRv()
        observeHotBeverages()
    }

    private fun setRv() {
        beveragesAdapter.itemClick = updateCallback
        binding.rvHotBeverages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = beveragesAdapter
        }
    }

    private fun observeHotBeverages() {
        lifecycleScope.launchWhenStarted {
            beverageViewModel.hotBeverages.observe(viewLifecycleOwner, Observer {
                beveragesAdapter.items.clear()
                beveragesAdapter.items.addAll(it)
                beveragesAdapter.notifyDataSetChanged()
            })
        }
    }
}