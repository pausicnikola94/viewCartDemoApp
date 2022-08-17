package com.example.viewdemoapp.feature_cart.presentation.shopping_cart

import com.example.viewdemoapp.feature_cart.presentation.models.ProductWrapper

data class ShoppingCartUiState(
    val products: List<ProductWrapper>? = null,
    val isLoading: Boolean? = false,
    val hasError: String? = null
)
