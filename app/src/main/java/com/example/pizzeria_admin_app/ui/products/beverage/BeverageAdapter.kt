package com.example.pizzeria_admin_app.ui.products.beverage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzeria_admin_app.data.remote.product.api.models.Beverage
import com.example.pizzeria_admin_app.databinding.BeverageItemBinding
import javax.inject.Inject

class BeverageAdapter @Inject constructor() :
    RecyclerView.Adapter<BeverageAdapter.BeverageViewHolder>() {
    inner class BeverageViewHolder(val binding: BeverageItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    lateinit var itemClick: (item: Beverage) -> Unit

    val items = mutableListOf<Beverage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeverageViewHolder {
        return BeverageViewHolder(
            BeverageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BeverageViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            clBeverageItem.setOnClickListener { itemClick(item) }
            tvBeverageName.text = item.name
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}