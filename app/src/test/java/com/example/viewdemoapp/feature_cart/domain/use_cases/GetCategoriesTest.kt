package com.example.viewdemoapp.feature_cart.domain.use_cases

import com.example.viewdemoapp.feature_cart.domain.models.main.Category
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private lateinit var fakeCategoryRepository: FakeCategoryRepository
private lateinit var getCategories: GetCategories

class GetCategoriesTest {
    @Before
    fun createRepository() {
        fakeCategoryRepository = FakeCategoryRepository()
        getCategories = GetCategories(fakeCategoryRepository)
    }

    @Test
    fun getCategorySections_categorySectionsReceived() = runBlocking {
        val result = getCategories.getCategorySection().first()
        Truth.assertThat(result.data?.get(0)).isInstanceOf(Category::class.java)
    }
}
