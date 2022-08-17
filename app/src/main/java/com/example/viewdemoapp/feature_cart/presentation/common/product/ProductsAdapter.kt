package com.example.viewdemoapp.feature_cart.presentation.common.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.viewdemoapp.R
import com.example.viewdemoapp.databinding.ItemProductCloathingBinding
import com.example.viewdemoapp.databinding.ItemProductElectronicsBinding
import com.example.viewdemoapp.databinding.ItemProductJewelryBinding
import com.example.viewdemoapp.databinding.ItemProductsBinding
import com.example.viewdemoapp.databinding.ItemShoppingCartBinding
import com.example.viewdemoapp.feature_cart.presentation.models.ProductDetails
import com.example.viewdemoapp.feature_cart.presentation.models.ProductWrapper

class ProductsAdapter(
    private val addListener: ((ProductDetails, Int) -> Unit)? = null,
    private val removeListener: ((ProductDetails, Int) -> Unit)? = null,
    private val productListener: (ProductDetails) -> Unit
) :
    ListAdapter<ProductWrapper, ProductViewHolder>(ProductComparator) {

    object ProductComparator : DiffUtil.ItemCallback<ProductWrapper>() {
        override fun areItemsTheSame(oldItem: ProductWrapper, newItem: ProductWrapper) =
            oldItem.product.id == newItem.product.id

        override fun areContentsTheSame(oldItem: ProductWrapper, newItem: ProductWrapper) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return when (viewType) {
            R.layout.item_products -> {
                ProductViewHolder.ProductMainHolder(
                    ItemProductsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            R.layout.item_shopping_cart -> {
                ProductViewHolder.ProductCartHolder(
                    ItemShoppingCartBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            R.layout.item_product_cloathing -> {
                ProductViewHolder.ProductCloathingHolder(
                    ItemProductCloathingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            R.layout.item_product_electronics -> {
                ProductViewHolder.ProductElectronicsHolder(
                    ItemProductElectronicsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            R.layout.item_product_jewelry -> {
                ProductViewHolder.ProductJewleryHolder(
                    ItemProductJewelryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> { throw IllegalArgumentException("Invalid View Type Provided") }
        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder.ProductMainHolder -> {
                holder.bind(getItem(position).product, addListener, removeListener, productListener)
            }
            is ProductViewHolder.ProductCartHolder -> {
                holder.bind(getItem(position).product, addListener, removeListener, productListener)
            }
            is ProductViewHolder.ProductCloathingHolder -> {
                holder.bind(getItem(position).product, productListener)
            }
            is ProductViewHolder.ProductElectronicsHolder -> {
                holder.bind(getItem(position).product, productListener)
            }
            is ProductViewHolder.ProductJewleryHolder -> {
                holder.bind(getItem(position).product, productListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProductWrapper.ProductMain -> R.layout.item_products
            is ProductWrapper.ProductCart -> R.layout.item_shopping_cart
            is ProductWrapper.ProductCloathing -> R.layout.item_product_cloathing
            is ProductWrapper.ProductElectronics -> R.layout.item_product_electronics
            is ProductWrapper.ProductJewlery -> R.layout.item_product_jewelry
        }
    }

    private var positionAdaper: Int? = null
    fun setClickedPosition(position: Int) { positionAdaper = position }

    fun updateValues(products: List<ProductWrapper>) {
        if (positionAdaper == null) {
            this.submitList(products)
        } else {
            if (this.itemCount > products.count()) {
                this.submitList(products)
                return
            }
            if (positionAdaper in products.indices) {
                adapterItemChanged(
                    products[positionAdaper!!].product,
                    positionAdaper!!
                )
            }
            positionAdaper = null
        }
    }

    private fun adapterItemChanged(product: ProductDetails?, position: Int) {
        val adapterProduct = this.getItem(position)
        if (product != null) {
            adapterProduct.product.quantity = product.quantity
        }
        this.notifyItemChanged(position, adapterProduct)
    }
}
