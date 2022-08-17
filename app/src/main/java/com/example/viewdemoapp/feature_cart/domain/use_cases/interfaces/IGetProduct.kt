package com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces

import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import kotlinx.coroutines.flow.Flow

interface IGetProduct {
    fun getProduct(product: Product): Flow<Product>
}
