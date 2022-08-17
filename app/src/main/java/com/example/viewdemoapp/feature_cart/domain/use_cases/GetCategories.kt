package com.example.viewdemoapp.feature_cart.domain.use_cases

import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.domain.models.main.Category
import com.example.viewdemoapp.feature_cart.domain.repository.ICategoryRepository
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IGetCategories
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategories @Inject constructor(
    private val categoryRepository: ICategoryRepository,
) : IGetCategories {

    override suspend fun getCategorySection(): Flow<Resource<List<Category>>> {
        return categoryRepository.getCategories()
    }
}
