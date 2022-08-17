package com.example.viewdemoapp.feature_cart.presentation.category_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IAddToCart
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IGetCategoryProducts
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IRemoveFromCart
import com.example.viewdemoapp.feature_cart.presentation.models.ProductDetails
import com.example.viewdemoapp.feature_cart.presentation.models.ProductWrapper
import com.example.viewdemoapp.feature_cart.presentation.models.toProduct
import com.example.viewdemoapp.feature_cart.presentation.models.toProductDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailsViewModel @Inject constructor (
    private val getCategoryProducts: IGetCategoryProducts,
    private val addToCart: IAddToCart,
    private val removeFromCart: IRemoveFromCart
) :
    ViewModel() {

    private var _categoryUiState = MutableStateFlow<CategoryUiState>(CategoryUiState(isLoading = true))
    val categoryUiState: StateFlow<CategoryUiState> = _categoryUiState

    private var _job: Job? = null

    fun setup(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getCategoryProducts.getCategoryProducts(categoryId)
                .collect { result ->
                    when (result.status) {
                        Resource.Status.SUCCESS -> {
                            val productWrapperList = result.data?.map {
                                ProductWrapper.ProductMain(it.toProductDetails())
                            }
                            _categoryUiState.value = CategoryUiState(categoryId, productWrapperList)
                        }
                        Resource.Status.ERROR -> {
                            _categoryUiState.value = CategoryUiState(hasError = result.message)
                        }
                        Resource.Status.LOADING -> {
                            _categoryUiState.value = CategoryUiState(isLoading = true)
                        }
                    }
                }
        }
    }

    fun addToCart(productDetails: ProductDetails) {
        _job?.cancel()
        _job = viewModelScope.launch(Dispatchers.IO) {
            addToCart.addProduct(productDetails.toProduct(), 0)
        }
    }

    fun removeFromCart(productDetails: ProductDetails) {
        _job?.cancel()
        _job = viewModelScope.launch(Dispatchers.IO) {
            removeFromCart.removeProduct(productDetails.toProduct(), 0)
        }
    }
}
