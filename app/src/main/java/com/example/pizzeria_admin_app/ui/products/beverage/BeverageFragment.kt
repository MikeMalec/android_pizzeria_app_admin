package com.example.pizzeria_admin_app.ui.products.beverage

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.api.models.Beverage
import com.example.pizzeria_admin_app.databinding.BeverageFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeverageFragment : BaseFragment(R.layout.beverage_fragment) {
    private lateinit var binding: BeverageFragmentBinding

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beverageViewModel.fetchBeverages()
        binding = BeverageFragmentBinding.bind(view)
        binding.fabCreateBeverage.setOnClickListener { createBeverage() }
        setVp()
    }

    private fun setVp() {
        viewPagerAdapter = ViewPagerAdapter(
            listOf(
                ColdBeveragesFragment().also { it.updateCallback = ::updateBeverage },
                HotBeveragesFragment().also { it.updateCallback = ::updateBeverage },
                AlcoholsFragment().also { it.updateCallback = ::updateBeverage }
            ),
            childFragmentManager,
            lifecycle
        )
        binding.vpBeverages.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tlBeverages, binding.vpBeverages) { tab, position ->
            val fragment = viewPagerAdapter.fragments[position] as NamedFragment
            tab.text = fragment.getName()
        }.attach()
        binding.tlBeverages.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.also {
                    viewPagerAdapter.createFragment(tab.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun createBeverage() {
        beverageViewModel.clearInfo()
        BeverageFragmentDirections.actionBeverageFragmentToCreateUpdateBeverageFragment(null).also {
            findNavController().navigate(it)
        }
    }

    private fun updateBeverage(beverage: Beverage) {
        beverageViewModel.clearInfo()
        BeverageFragmentDirections.actionBeverageFragmentToCreateUpdateBeverageFragment(beverage)
            .also {
                findNavController().navigate(it)
            }
    }
}