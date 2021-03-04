package com.example.pizzeria_admin_app.ui.order

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.order.api.models.Order
import com.example.pizzeria_admin_app.data.utils.Resource
import com.example.pizzeria_admin_app.databinding.OrderFragmentBinding
import com.example.pizzeria_admin_app.ui.common.BaseFragment
import com.example.pizzeria_admin_app.utils.extensions.gone
import com.example.pizzeria_admin_app.utils.extensions.show
import com.example.pizzeria_admin_app.utils.time.DateConverter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import javax.inject.Inject

@AndroidEntryPoint
class OrderFragment : BaseFragment(R.layout.order_fragment) {

    private lateinit var binding: OrderFragmentBinding

    private val orderViewModel: OrderViewModel by viewModels()

    private val args: OrderFragmentArgs by navArgs()

    private val order: Order
        get() = args.order

    @Inject
    lateinit var dateConverter: DateConverter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = OrderFragmentBinding.bind(view)
        orderViewModel.initOrder(order)
        setViews()
        observeOrderStatus()
        observeRequestState()
        observeOrderStateChanged()
    }

    private fun setViews() {
        binding.apply {
            if (order.orderInfo.status != "new") {
                btnAcceptOrder.gone()
                btnCancelOrder.gone()
            } else {
                btnAcceptOrder.setOnClickListener {
                    orderViewModel.setOrderStatus("accepted")
                }
                btnCancelOrder.setOnClickListener {
                    orderViewModel.setOrderStatus("canceled")
                }
            }
            tvOrderStatus.text = order.orderInfo.status
            tvOrderDate.text = dateConverter.mapDateToYearMonthHours(order.orderInfo.createdAt)
            tvOrderInfo.text = order.getDescription()
            tvStreetName.text = order.orderInfo.street
            tvHouseNumber.text = order.orderInfo.houseNumber
            tvCityName.text = order.orderInfo.city
            tvPhoneNumber.text = order.orderInfo.phoneNumber
            tvPaymentMethod.text = order.orderInfo.paymentMethod
        }
    }

    private fun observeOrderStatus() {
        lifecycleScope.launchWhenStarted {
            orderViewModel.orderStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    "new" -> {
                        setOrderStatus("Nowe")
                        binding.tvOrderStatus.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green
                            )
                        )
                    }
                    "accepted" -> {
                        setOrderStatus("Zaakceptowane")
                        binding.tvOrderStatus.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green
                            )
                        )
                    }
                    "canceled" -> {
                        binding.tvOrderStatus.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red
                            )
                        )
                        setOrderStatus("Anulowane")
                    }
                }
            })
        }
    }

    private fun observeRequestState() {
        lifecycleScope.launchWhenStarted {
            orderViewModel.orderStatusRequestState.consumeAsFlow().collect {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun observeOrderStateChanged() {
        lifecycleScope.launchWhenStarted {
            orderViewModel.orderStateChanged.consumeAsFlow().collect {
                if (it) {
                    mainViewModel.removeOrderFromNewOrders(order)
                }
            }
        }
    }

    private fun setOrderStatus(status: String) {
        binding.tvOrderStatus.text = status
    }

    private fun showLoading() {
        binding.pbOrderLoading.show()
    }

    private fun hideLoading() {
        binding.pbOrderLoading.gone()
    }
}