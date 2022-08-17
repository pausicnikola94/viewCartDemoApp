package com.example.viewdemoapp.feature_cart.presentation.main

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.viewdemoapp.databinding.ItemCategoryClothingBinding
import com.example.viewdemoapp.databinding.ItemCategoryElectronicsBinding
import com.example.viewdemoapp.databinding.ItemCategoryJeweleryBinding
import com.example.viewdemoapp.databinding.ItemCategoryNameBinding
import com.example.viewdemoapp.feature_cart.presentation.common.product.ProductsAdapter
import com.example.viewdemoapp.feature_cart.presentation.models.CategorySection

sealed class MainViewHolder(
    binding: ViewBinding,
    val layMan: LinearLayoutManager? = null,
    var adap: ProductsAdapter? = null
) : RecyclerView.ViewHolder(binding.root) {

    class CategorySectionClothingHolder(
        private val binding: ItemCategoryClothingBinding,
        val context: Context,
        private val layoutManager: LinearLayoutManager,
        var adapter: ProductsAdapter? = null
    ) :
        MainViewHolder(binding, layoutManager, adapter) {
        fun bind(
            categorySection: CategorySection
        ) {
            if (binding.categoryProductsRecyclerView.layoutManager == null) {
                binding.categoryProductsRecyclerView.layoutManager = layoutManager
            }
            binding.categoryProductsRecyclerView.adapter = adapter
            adapter?.submitList(categorySection.products)
        }
    }

    class CategorySectionJeweleryHolder(
        private val binding: ItemCategoryJeweleryBinding,
        val context: Context,
        private val layoutManager: LinearLayoutManager,
        var adapter: ProductsAdapter? = null
    ) :
        MainViewHolder(binding, layoutManager, adapter) {
        fun bind(
            categorySection: CategorySection
        ) {
            if (binding.categoryProductsRecyclerView.layoutManager == null) {
                binding.categoryProductsRecyclerView.layoutManager = layoutManager
            }
            binding.categoryProductsRecyclerView.adapter = adapter
            adapter?.submitList(categorySection.products)
        }
    }

    class CategorySectionElectronicsHolder(
        private val binding: ItemCategoryElectronicsBinding,
        val context: Context,
        private val layoutManager: LinearLayoutManager,
        var adapter: ProductsAdapter? = null
    ) :
        MainViewHolder(binding, layoutManager, adapter) {
        fun bind(
            categorySection: CategorySection
        ) {
            if (binding.categoryProductsRecyclerView.layoutManager == null) {
                binding.categoryProductsRecyclerView.layoutManager = layoutManager
            }
            binding.categoryProductsRecyclerView.adapter = adapter
            adapter?.submitList(categorySection.products)
        }
    }

    class CategorySectionNameHolder(
        private val binding: ItemCategoryNameBinding,
        val context: Context
    ) :
        MainViewHolder(binding, null, null) {
        fun bind(
            categorySectionId: String,
            categoryListener: (String) -> Unit
        ) {
            binding.textView.text = categorySectionId.replaceFirstChar { it.uppercase() }
            binding.itemCategoryName.setOnClickListener {
                categoryListener(categorySectionId)
            }
        }
    }
}
