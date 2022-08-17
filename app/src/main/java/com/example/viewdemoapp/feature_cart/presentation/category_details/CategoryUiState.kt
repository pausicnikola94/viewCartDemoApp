package com.example.viewdemoapp.feature_cart.presentation.category_details

import com.example.viewdemoapp.feature_cart.presentation.models.ProductWrapper

data class CategoryUiState(
    val id: String? = null,
    val products: List<ProductWrapper>? = null,
    val isLoading: Boolean? = false,
    val hasError: String? = null
)
