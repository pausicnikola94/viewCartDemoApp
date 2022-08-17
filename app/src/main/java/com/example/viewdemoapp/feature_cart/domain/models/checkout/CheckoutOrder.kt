package com.example.viewdemoapp.feature_cart.domain.models.checkout

data class CheckoutOrder(
    val id: Int = 0,
    val products: List<ProductOrder>,
    val productsUI: MutableList<Product>? = null
)
