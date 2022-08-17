package com.example.viewdemoapp.feature_cart.data.remote.dto

import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product

data class ProductDto(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
) {
    fun toProduct(): Product {
        return Product(this.id, this.title, this.description, this.image, 0)
    }
}
