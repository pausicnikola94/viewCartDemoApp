package com.example.viewdemoapp.feature_cart.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.domain.models.main.Category
import com.example.viewdemoapp.feature_cart.domain.models.main.CategoryType
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IGetCategories
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IGetCheckoutOrder
import com.example.viewdemoapp.feature_cart.presentation.models.CategorySection
import com.example.viewdemoapp.feature_cart.presentation.models.ProductWrapper
import com.example.viewdemoapp.feature_cart.presentation.models.toProductDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategorySections: IGetCategories,
    private val getCheckoutOrder: IGetCheckoutOrder
) : ViewModel() {

    private var _mainUiState = MutableStateFlow<MainUiState>(MainUiState(isLoading = true))
    val mainUiState: StateFlow<MainUiState> = _mainUiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getCheckoutOrder.init()
            setupCategorySectionObserver()
            setupCheckoutOrderObserver()
        }
    }

    private suspend fun setupCategorySectionObserver() {
        getCategorySections.getCategorySection()
            .collect { result ->
                when (result.status) {
                    Resource.Status.SUCCESS -> {
                        _mainUiState.value = MainUiState(getCategorySectionList(result.data))
                    }
                    Resource.Status.ERROR -> {
                        _mainUiState.value = MainUiState(hasError = result.message)
                    }
                    Resource.Status.LOADING -> {
                        _mainUiState.value = MainUiState(isLoading = true)
                    }
                }
            }
    }

    private fun getCategorySectionList(categories: List<Category>?): List<CategorySection> {
        val catSections = mutableListOf<CategorySection>()
        var productWrapperList: List<ProductWrapper>
        categories?.forEach { category ->
            catSections.add(CategorySection.CategorySectionName(category.id))
            when (category.categoryType) {
                CategoryType.ELECTRONICS -> {
                    ArrayList(category.products).let { productList ->
                        productWrapperList = productList.map { product ->
                            ProductWrapper.ProductElectronics(
                                product.toProductDetails()
                            )
                        }
                        catSections.add(
                            CategorySection.CategorySectionElectronics(
                                category.id, productWrapperList
                            )
                        )
                    }
                }
                CategoryType.JEWELERY -> {
                    ArrayList(category.products).let { productList ->
                        productWrapperList = productList.map { product ->
                            ProductWrapper.ProductJewlery(
                                product.toProductDetails()
                            )
                        }
                        catSections.add(
                            CategorySection.CategorySectionJewelery(
                                category.id, productWrapperList
                            )
                        )
                    }
                }
                else -> {
                    ArrayList(category.products).let { productList ->
                        productWrapperList = productList.map { product ->
                            ProductWrapper.ProductCloathing(
                                product.toProductDetails()
                            )
                        }
                        catSections.add(
                            CategorySection.CategorySectionClothing(
                                category.id, productWrapperList
                            )
                        )
                    }
                }
            }
        }
        return catSections
    }

    private suspend fun setupCheckoutOrderObserver() {
        getCheckoutOrder.getCheckoutOrder()
            .collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        _mainUiState.value = _mainUiState.value
                            .copy(hasCheckoutOrder = it.data?.isEmpty() == false)
                    }
                    Resource.Status.ERROR -> {}
                    Resource.Status.LOADING -> {}
                }
            }
    }
}
