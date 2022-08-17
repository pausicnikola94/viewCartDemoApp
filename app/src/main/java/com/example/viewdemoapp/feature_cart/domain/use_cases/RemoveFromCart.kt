package com.example.viewdemoapp.feature_cart.domain.use_cases

import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.repository.ICheckoutOrderRepository
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IRemoveFromCart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveFromCart @Inject constructor(
    private val checkoutOrderRepository: ICheckoutOrderRepository
) : IRemoveFromCart {
    override fun removeProduct(product: Product, checkoutOrderId: Int) {
        if (product.quantity - 1 >= 0) {
            checkoutOrderRepository.removeFromCart(product.copy(quantity = product.quantity - 1), checkoutOrderId)
        }
    }
}
