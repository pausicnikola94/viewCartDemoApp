package com.example.viewdemoapp.feature_cart.presentation.main

import com.example.viewdemoapp.feature_cart.presentation.models.CategorySection
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private lateinit var mainViewModel: MainViewModel
private lateinit var fakeGetCategorySections: FakeGetCategories
private lateinit var fakeIGetCheckoutOrder: FakeGetCheckoutOrder

class MainViewModelTest {
    @Before
    fun createRepository() {
        fakeGetCategorySections = FakeGetCategories()
        fakeIGetCheckoutOrder = FakeGetCheckoutOrder()
        mainViewModel = MainViewModel(fakeGetCategorySections, fakeIGetCheckoutOrder)
    }

    @Test
    fun setupMain_categorySectionPresent() = runBlocking {
        val result = mainViewModel.mainUiState.drop(1).first()
        assertThat(result.categorySectionList?.get(1)).isInstanceOf(CategorySection.CategorySectionElectronics::class.java)
    }
}
