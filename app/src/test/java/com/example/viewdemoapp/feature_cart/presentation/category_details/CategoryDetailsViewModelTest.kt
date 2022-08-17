package com.example.viewdemoapp.feature_cart.presentation.category_details

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private lateinit var categoryDetailsViewModel: CategoryDetailsViewModel

class CategoryDetailsViewModelTest {
    @Before
    fun createRepository() {
        categoryDetailsViewModel = CategoryDetailsViewModel(
            FakeGetCategoryProducts(),
            FakeAddToCart(),
            FakeRemoveFromCart()
        )
    }

    @Test
    fun setupCategoryDetails_categoryDetailsTitlePresent() = runBlocking {
        categoryDetailsViewModel.setup("electronics")
        val result = categoryDetailsViewModel.categoryUiState.drop(1).first()
        assertThat(result.id).isEqualTo("electronics")
    }
}
