package com.example.pizzeria_admin_app.ui.neworders

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.order.api.models.Order
import com.example.pizzeria_admin_app.data.remote.order.service.OrderService
import com.example.pizzeria_admin_app.databinding.NewOrdersFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class NewOrdersFragment : BaseFragment(R.layout.new_orders_fragment) {
    private lateinit var binding: NewOrdersFragmentBinding

    @Inject
    lateinit var ordersAdapter: OrdersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NewOrdersFragmentBinding.bind(view)
        binding.srlNewOrders.setOnRefreshListener {
            binding.srlNewOrders.isRefreshing = true
            mainActivity.restartSocket()
            mainViewModel.fetchNewOrders()
        }
        setRv()
        observeNewOrders()
    }

    private fun stopRefreshing() {
        binding.srlNewOrders.isRefreshing = false
    }

    private fun setRv() {
        ordersAdapter.itemClick = ::navigateToOrder
        binding.rvNewOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ordersAdapter
        }
    }

    private fun observeNewOrders() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.newOrders.collect {
                stopRefreshing()
                OrderService.currentAmountOfOrders.postValue(it.size)
                ordersAdapter.submitOrders(it)
            }
        }
    }

    private fun navigateToOrder(order: Order) {
        NewOrdersFragmentDirections.actionNewOrdersFragmentToOrderFragment(order).also {
            findNavController().navigate(it)
        }
    }
}