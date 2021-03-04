package com.example.pizzeria_admin_app.ui.oldorders

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.order.api.models.Order
import com.example.pizzeria_admin_app.databinding.OldOrdersFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.example.pizzeria_admin_app.ui.neworders.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OldOrdersFragment : BaseFragment(R.layout.old_orders_fragment),
    SearchView.OnQueryTextListener {

    private lateinit var binding: OldOrdersFragmentBinding

    @Inject
    lateinit var ordersAdapter: OrdersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.old_orders_menu, menu)
        val search = menu.findItem(R.id.searchOldOrders)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener {
            mainViewModel.clearOrderFiltering()
            false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.fetchOldOrdersIfEmpty()
        binding = OldOrdersFragmentBinding.bind(view)
        binding.srlOldOrders.setOnRefreshListener {
            binding.srlOldOrders.isRefreshing = true
            mainViewModel.resetOldOrders()
        }
        setRv()
        observeOldOrders()
        observeFilteredOldOrders()
    }

    private fun stopRefreshing() {
        binding.srlOldOrders.isRefreshing = false
    }

    private fun setRv() {
        ordersAdapter.itemClick = ::navigateToOrderFragment
        binding.rvOldOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ordersAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val ll = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisible = ll.findLastVisibleItemPosition()
                    if (lastVisible == ordersAdapter.itemCount - 1 || lastVisible == ordersAdapter.itemCount - 2) {
                        when (mainViewModel.filteringOldOrders()) {
                            true -> {
                                mainViewModel.query?.also { query ->
                                    mainViewModel.fetchFilteredOldOrders(query)
                                }
                            }
                            false -> mainViewModel.fetchOldOrders()
                        }
                    }
                }
            })
        }
    }

    private fun observeOldOrders() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.oldOrders.observe(viewLifecycleOwner, Observer {
                stopRefreshing()
                if (!mainViewModel.filteringOldOrders()) {
                    ordersAdapter.submitOrders(it)
                }
            })
        }
    }

    private fun observeFilteredOldOrders() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.filteredOldOrders.observe(viewLifecycleOwner, Observer {
                if (mainViewModel.filteringOldOrders()) {
                    ordersAdapter.submitOrders(it)
                }
            })
        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(input: String?): Boolean {
        when (input) {
            "", null -> mainViewModel.clearOrderFiltering()
            else -> mainViewModel.queryChannel.offer(input)
        }
        return true
    }

    private fun navigateToOrderFragment(order: Order) {
        OldOrdersFragmentDirections.actionOldOrdersFragmentToOrderFragment(order).also {
            findNavController().navigate(it)
        }
    }
}