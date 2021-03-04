package com.example.pizzeria_admin_app.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzeria_admin_app.data.remote.product.Product
import com.example.pizzeria_admin_app.databinding.ProductItemBinding
import javax.inject.Inject

class ProductAdapter @Inject constructor() :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    lateinit var itemCallback: (item: Product) -> Unit

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.getEquals(newItem)
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.getEquals(newItem)
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitList(items: List<Product>) = differ.submitList(items)
    fun getItems() = differ.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItems()[position]
        holder.binding.apply {
            clItem.setOnClickListener { itemCallback(item) }
            tvProductName.text = item.getProductName()
        }
    }

    override fun getItemCount(): Int {
        return getItems().size
    }
}