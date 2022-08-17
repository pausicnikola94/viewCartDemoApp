package com.example.viewdemoapp.feature_cart.domain.repository

import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.domain.models.main.Category
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {
    fun getCategories(): Flow<Resource<List<Category>>>
}
