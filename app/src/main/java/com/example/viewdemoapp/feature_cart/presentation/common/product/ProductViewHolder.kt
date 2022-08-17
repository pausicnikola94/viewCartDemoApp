package com.example.viewdemoapp.feature_cart.presentation.common.product

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.viewdemoapp.databinding.ItemProductCloathingBinding
import com.example.viewdemoapp.databinding.ItemProductElectronicsBinding
import com.example.viewdemoapp.databinding.ItemProductJewelryBinding
import com.example.viewdemoapp.databinding.ItemProductsBinding
import com.example.viewdemoapp.databinding.ItemShoppingCartBinding
import com.example.viewdemoapp.feature_cart.presentation.common.glide.GlideApp
import com.example.viewdemoapp.feature_cart.presentation.models.ProductDetails

sealed class ProductViewHolder(
    private val binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {
    class ProductMainHolder(private val binding: ItemProductsBinding) : ProductViewHolder(binding) {
        fun bind(
            product: ProductDetails,
            addListener: ((ProductDetails, Int) -> Unit)? = null,
            removeListener: ((ProductDetails, Int) -> Unit)? = null,
            productClicked: (ProductDetails) -> Unit
        ) {
            GlideApp.with(binding.root)
                .load(product.image)
                .fitCenter()
                .into(binding.imageButton)

            binding.addToCartCad.currentAmountCard.text = product.quantity.toString()

            binding.imageButton.setOnClickListener {
                productClicked(product)
            }

            binding.addToCartCad.add.setOnClickListener {
                if (addListener != null) {
                    addListener(product, adapterPosition)
                }
            }

            binding.addToCartCad.remove.setOnClickListener {
                if (removeListener != null) {
                    removeListener(product, adapterPosition)
                }
            }
        }
    }
    class ProductCartHolder(private val binding: ItemShoppingCartBinding) : ProductViewHolder(binding) {
        fun bind(
            product: ProductDetails,
            addListener: ((ProductDetails, Int) -> Unit)? = null,
            removeListener: ((ProductDetails, Int) -> Unit)? = null,
            productClicked: (ProductDetails) -> Unit
        ) {
            GlideApp.with(binding.root)
                .load(product.image)
                .fitCenter()
                .into(binding.imageButton)

            binding.addToCartCad.currentAmountCard.text = product.quantity.toString()

            binding.imageButton.setOnClickListener {
                productClicked(product)
            }

            binding.addToCartCad.add.setOnClickListener {
                if (addListener != null) {
                    addListener(product, adapterPosition)
                }
            }

            binding.addToCartCad.remove.setOnClickListener {
                if (removeListener != null) {
                    removeListener(product, adapterPosition)
                }
            }
        }
    }
    class ProductCloathingHolder(private val binding: ItemProductCloathingBinding) : ProductViewHolder(binding) {
        fun bind(
            product: ProductDetails,
            productClicked: (ProductDetails) -> Unit
        ) {
            GlideApp.with(binding.root)
                .load(product.image)
                .fitCenter()
                .into(binding.imageButton)

            binding.imageButton.setOnClickListener {
                productClicked(product)
            }
        }
    }
    class ProductElectronicsHolder(private val binding: ItemProductElectronicsBinding) : ProductViewHolder(binding) {
        fun bind(
            product: ProductDetails,
            productClicked: (ProductDetails) -> Unit
        ) {
            GlideApp.with(binding.root)
                .load(product.image)
                .fitCenter()
                .into(binding.imageButton)

            binding.imageButton.setOnClickListener {
                productClicked(product)
            }
        }
    }
    class ProductJewleryHolder(private val binding: ItemProductJewelryBinding) : ProductViewHolder(binding) {
        fun bind(
            product: ProductDetails,
            productClicked: (ProductDetails) -> Unit
        ) {
            GlideApp.with(binding.root)
                .load(product.image)
                .fitCenter()
                .into(binding.imageButton)

            binding.imageButton.setOnClickListener {
                productClicked(product)
            }
        }
    }
}
