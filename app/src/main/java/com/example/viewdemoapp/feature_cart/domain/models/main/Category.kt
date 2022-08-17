package com.example.viewdemoapp.feature_cart.domain.models.main

import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product

data class Category(
    val id: String,
    val products: List<Product>,
    val categoryType: CategoryType
)
