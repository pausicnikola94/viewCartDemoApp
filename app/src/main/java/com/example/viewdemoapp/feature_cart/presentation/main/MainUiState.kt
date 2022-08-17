package com.example.viewdemoapp.feature_cart.presentation.main

import com.example.viewdemoapp.feature_cart.presentation.models.CategorySection

data class MainUiState(
    val categorySectionList: List<CategorySection>? = null,
    val hasCheckoutOrder: Boolean? = false,
    val isLoading: Boolean? = false,
    val hasError: String? = null
)
