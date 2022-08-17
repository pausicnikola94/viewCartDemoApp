package com.example.viewdemoapp.feature_cart.presentation.main

import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.viewdemoapp.R
import com.example.viewdemoapp.databinding.ItemCategoryClothingBinding
import com.example.viewdemoapp.databinding.ItemCategoryElectronicsBinding
import com.example.viewdemoapp.databinding.ItemCategoryJeweleryBinding
import com.example.viewdemoapp.databinding.ItemCategoryNameBinding
import com.example.viewdemoapp.feature_cart.presentation.common.product.ProductsAdapter
import com.example.viewdemoapp.feature_cart.presentation.models.CategorySection
import com.example.viewdemoapp.feature_cart.presentation.models.ProductDetails

class MainAdapter(
    private val productListener: (ProductDetails, Int) -> Unit,
    private val categoryListener: (String) -> Unit
) :
    ListAdapter<CategorySection, MainViewHolder>(CategorySectionComparator) {
    private val positionList = SparseIntArray()

    object CategorySectionComparator : DiffUtil.ItemCallback<CategorySection>() {
        override fun areItemsTheSame(oldItem: CategorySection, newItem: CategorySection) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CategorySection, newItem: CategorySection) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return when (viewType) {
            R.layout.item_category_electronics -> MainViewHolder.CategorySectionElectronicsHolder(
                ItemCategoryElectronicsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                parent.context,
                LinearLayoutManager(
                    parent.context, LinearLayoutManager.HORIZONTAL, false
                )
            )
            R.layout.item_category_jewelery -> MainViewHolder.CategorySectionJeweleryHolder(
                ItemCategoryJeweleryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                parent.context,
                LinearLayoutManager(
                    parent.context, LinearLayoutManager.HORIZONTAL, false
                )
            )
            R.layout.item_category_clothing -> MainViewHolder.CategorySectionClothingHolder(
                ItemCategoryClothingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                parent.context,
                LinearLayoutManager(
                    parent.context, LinearLayoutManager.HORIZONTAL, false
                )
            )
            R.layout.item_category_name -> MainViewHolder.CategorySectionNameHolder(
                ItemCategoryNameBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                parent.context
            )
            else -> { throw IllegalArgumentException("Invalid View Type Provided") }
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val lastSeenFirstPosition: Int = positionList.get(position, 0)
        if (lastSeenFirstPosition >= 0) {
            holder.layMan?.scrollToPositionWithOffset(lastSeenFirstPosition, 0)
        }

        when (holder) {
            is MainViewHolder.CategorySectionClothingHolder -> {
                holder.adapter = holder.adapter ?: ProductsAdapter { product ->
                    productListener(product, holder.adapterPosition)
                }
                holder.bind(getItem(holder.adapterPosition))
            }
            is MainViewHolder.CategorySectionElectronicsHolder -> {
                holder.adapter = holder.adapter ?: ProductsAdapter { product ->
                    productListener(product, holder.adapterPosition)
                }
                holder.bind(getItem(holder.adapterPosition))
            }
            is MainViewHolder.CategorySectionJeweleryHolder -> {
                holder.adapter = holder.adapter ?: ProductsAdapter { product ->
                    productListener(product, holder.adapterPosition)
                }
                holder.bind(getItem(holder.adapterPosition))
            }
            is MainViewHolder.CategorySectionNameHolder -> {
                holder.bind(
                    getItem(holder.adapterPosition).id ?: ""
                ) { categoryId -> categoryListener(categoryId) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CategorySection.CategorySectionElectronics -> R.layout.item_category_electronics
            is CategorySection.CategorySectionClothing -> R.layout.item_category_clothing
            is CategorySection.CategorySectionJewelery -> R.layout.item_category_jewelery
            is CategorySection.CategorySectionName -> R.layout.item_category_name
        }
    }

    override fun onViewRecycled(holder: MainViewHolder) {
        if (holder.layMan != null) {
            val position: Int = holder.adapterPosition
            val tempPos = holder.layMan.findFirstVisibleItemPosition()
            if (tempPos != 0) tempPos + 1
            val firstVisiblePosition: Int = tempPos
            positionList.put(position, firstVisiblePosition)
        }
        super.onViewRecycled(holder)
    }
}
