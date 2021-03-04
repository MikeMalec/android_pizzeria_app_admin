package com.example.pizzeria_admin_app.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pizzeria_admin_app.R
import com.example.pizzeria_admin_app.data.remote.product.ProductCategory
import com.example.pizzeria_admin_app.databinding.CategoryItemBinding
import javax.inject.Inject

class CategoryAdapter @Inject constructor() :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    lateinit var itemCallback: (category: ProductCategory) -> Unit

    var categories = listOf(
        ProductCategory("Pizze", R.drawable.pizza_toolbar),
        ProductCategory("Burgery", R.drawable.burger_toolbar),
        ProductCategory("Makarony", R.drawable.pasta_toolbar),
        ProductCategory("Pity", R.drawable.pita_toolbar),
        ProductCategory("Sałatki", R.drawable.salad_toolbar),
        ProductCategory("Napoje", R.drawable.cold_toolbar)
    )

    inner class CategoryViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.apply {
            clCategory.setOnClickListener { itemCallback(category) }
            ivCategoryImage.load(category.image) { crossfade(300) }
            tvCategoryName.text = category.name
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}