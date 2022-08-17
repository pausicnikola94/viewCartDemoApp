package com.example.viewdemoapp.feature_cart.data.repository.category

import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.data.remote.dto.ProductDto
import com.example.viewdemoapp.feature_cart.data.repository.CategoryRepository
import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CategoryRepositoryTest {
    private lateinit var categoryRemoteDataSource: FakeCategoryRemoteDataSource
    private lateinit var categoryRepository: CategoryRepository

    @Before
    fun createRepository() {
        categoryRemoteDataSource = FakeCategoryRemoteDataSource()
        categoryRepository = CategoryRepository(categoryRemoteDataSource)
    }
    @Test
    fun getCategories_categoriesReceived() = runBlocking {
        categoryRemoteDataSource.setFakeValues(
            Resource.success(listOf("electronics")),
            Resource.success(listOf(ProductDto("shirt", "", "", "")))
        )

        val result = categoryRepository.getCategories().first()
        assertThat(result.data?.get(0)?.id).isEqualTo("electronics")
    }
    @Test
    fun getCategories_categoriesProductsReceived() = runBlocking {
        categoryRemoteDataSource.setFakeValues(
            Resource.success(listOf("electronics")),
            Resource.success(listOf(ProductDto("shirt", "", "", "")))
        )

        val result = categoryRepository.getCategories().first()
        assertThat(result.data?.get(0)?.products?.get(0))
            .isEqualTo(Product("shirt", "", "", "", 0))
    }
}
