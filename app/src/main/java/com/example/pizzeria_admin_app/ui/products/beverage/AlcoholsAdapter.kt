package com.example.pizzeria_admin_app.ui.products.beverage

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzeria_admin_app.data.local.product.AlcoholGroup
import com.example.pizzeria_admin_app.data.remote.product.api.models.Beverage
import com.example.pizzeria_admin_app.databinding.AlcoholGroupItemBinding
import javax.inject.Inject

class AlcoholsAdapter @Inject constructor() :
    RecyclerView.Adapter<AlcoholsAdapter.AlcoholViewHolder>() {
    inner class AlcoholViewHolder(val binding: AlcoholGroupItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    lateinit var itemClick: (beverage: Beverage) -> Unit

    val items = mutableListOf<AlcoholGroup>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlcoholViewHolder {
        return AlcoholViewHolder(
            AlcoholGroupItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlcoholViewHolder, position: Int) {
        val item = items[position]
        Log.d("XXX","ITEM = $item")
        holder.binding.apply {
            tvGroupName.text = item.groupName
            rvBeverages.apply {
                layoutManager = LinearLayoutManager(this.context)
                val beverageAdapter = BeverageAdapter()
                beverageAdapter.itemClick = itemClick
                isNestedScrollingEnabled = false
                adapter = beverageAdapter
                beverageAdapter.items.addAll(item.alcohols)
                beverageAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}