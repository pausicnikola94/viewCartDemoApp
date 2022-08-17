package com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces

import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.domain.models.main.Category
import kotlinx.coroutines.flow.Flow

interface IGetCategories {
    suspend fun getCategorySection(): Flow<Resource<List<Category>>>
}
