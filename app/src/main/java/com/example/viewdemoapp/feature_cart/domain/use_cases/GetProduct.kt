package com.example.viewdemoapp.feature_cart.domain.use_cases

import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.models.checkout.ProductOrder
import com.example.viewdemoapp.feature_cart.domain.repository.ICheckoutOrderRepository
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IGetProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProduct @Inject constructor(
    private val checkoutOrderRepository: ICheckoutOrderRepository
) : IGetProduct {

    override fun getProduct(product: Product): Flow<Product> {
        return checkoutOrderRepository.getProductOrder(product.id).transform {
            emit(createProductDetailsUiState(it, product))
        }
    }

    private fun createProductDetailsUiState(it: ProductOrder?, product: Product): Product {
        return product.copy(quantity = it?.quantity ?: product.quantity)
    }
}
