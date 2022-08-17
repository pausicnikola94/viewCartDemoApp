package com.example.viewdemoapp.feature_cart.domain.use_cases

import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.models.main.Category
import com.example.viewdemoapp.feature_cart.domain.models.main.CategoryType
import com.example.viewdemoapp.feature_cart.domain.repository.ICategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCategoryRepository : ICategoryRepository {
    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(
            Resource.success(
                listOf(
                    Category(
                        "electronics",
                        listOf(Product("shirt", "", "", "", 0)),
                        CategoryType.ELECTRONICS
                    )
                )
            )
        )
    }
}
