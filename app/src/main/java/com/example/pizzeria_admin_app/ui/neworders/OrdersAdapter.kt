package com.example.pizzeria_admin_app.ui.neworders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.order.api.models.Order
import com.example.pizzeria_admin_app.databinding.OrderItemBinding
import com.example.pizzeria_admin_app.utils.extensions.gone
import com.example.pizzeria_admin_app.utils.extensions.show
import com.example.pizzeria_admin_app.utils.time.DateConverter
import javax.inject.Inject

class OrdersAdapter @Inject constructor(val dateConverter: DateConverter) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {
    inner class OrderViewHolder(val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    lateinit var itemClick: (order: Order) -> Unit

    private val diffCallback = object : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitOrders(orders: List<Order>) {
        differ.submitList(orders)
        notifyDataSetChanged()
    }

    fun getOrders() = differ.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            OrderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getOrders()[position]
        holder.binding.apply {
            cvOrder.setOnClickListener {
                itemClick(order)
            }
            ivHideOrderInfo.setOnClickListener {
                clOrderInfo.gone()
                ivHideOrderInfo.gone()
                ivShowOrderInfo.show()
            }
            ivShowOrderInfo.setOnClickListener {
                clOrderInfo.show()
                ivShowOrderInfo.gone()
                ivHideOrderInfo.show()
            }
            tvOrderInfo.text = order.getDescription()
            when (order.orderInfo.status) {
                "new" -> {
                    tvOrderStatus.text = "Nowe"
                    tvOrderStatus.setTextColor(ContextCompat.getColor(tvOrderStatus.context, R.color.green))
                }
                "accepted" -> {
                    tvOrderStatus.text = "Zaakceptowane"
                    tvOrderStatus.setTextColor(ContextCompat.getColor(tvOrderStatus.context, R.color.green))
                }
                "canceled" -> {
                    tvOrderStatus.text = "Anulowane"
                    tvOrderStatus.setTextColor(ContextCompat.getColor(tvOrderStatus.context, R.color.red))
                }
            }
            tvOrderCreatedAt.text = dateConverter.mapDateToHours(order.orderInfo.createdAt)
            tvStreet.text = order.orderInfo.street
            tvHouseNumber.text = order.orderInfo.houseNumber
            tvCity.text = order.orderInfo.city
            tvPhoneNumber.text = order.orderInfo.phoneNumber
            if (order.orderInfo.paymentMethod == "cash") {
                tvPaymentMethod.text = "Gotówka"
            } else {
                tvPaymentMethod.text = "Karta"
            }
        }
    }

    override fun getItemCount(): Int {
        return getOrders().size
    }
}