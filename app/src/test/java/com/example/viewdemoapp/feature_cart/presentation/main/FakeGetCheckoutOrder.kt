package com.example.viewdemoapp.feature_cart.presentation.main

import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IGetCheckoutOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetCheckoutOrder : IGetCheckoutOrder {
    override suspend fun getCheckoutOrder(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.success(listOf(Product("shirt", "", "", "", 0))))
    }

    override suspend fun init() { }
}
