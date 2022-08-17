package com.example.viewdemoapp.feature_cart.domain.models.checkout

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    var quantity: Int
)
