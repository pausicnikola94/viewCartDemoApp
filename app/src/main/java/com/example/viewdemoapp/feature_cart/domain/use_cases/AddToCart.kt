package com.example.viewdemoapp.feature_cart.domain.use_cases

import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.repository.ICheckoutOrderRepository
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IAddToCart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddToCart@Inject constructor(
    private val checkoutOrderRepository: ICheckoutOrderRepository
) : IAddToCart {
    override fun addProduct(product: Product, checkoutOrderId: Int) {
        checkoutOrderRepository.addToCart(product.copy(quantity = product.quantity + 1), checkoutOrderId)
    }
}
