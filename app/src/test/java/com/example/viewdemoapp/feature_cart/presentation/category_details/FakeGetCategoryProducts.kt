package com.example.viewdemoapp.feature_cart.presentation.category_details

import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IGetCategoryProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetCategoryProducts : IGetCategoryProducts {
    override suspend fun getCategoryProducts(categoryId: String): Flow<Resource<List<Product>>> = flow {
        emit(
            Resource.success(
                listOf(
                    Product("9", "", "", "", 0)
                )
            )
        )
    }
}
