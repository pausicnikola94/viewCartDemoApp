package com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces

import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import kotlinx.coroutines.flow.Flow

interface IGetCheckoutOrder {
    suspend fun getCheckoutOrder(): Flow<Resource<List<Product>>>
    suspend fun init()
}
