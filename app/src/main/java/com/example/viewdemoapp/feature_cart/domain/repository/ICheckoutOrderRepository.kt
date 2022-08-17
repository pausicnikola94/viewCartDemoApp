package com.example.viewdemoapp.feature_cart.domain.repository

import com.example.viewdemoapp.feature_cart.domain.models.checkout.CheckoutOrder
import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.models.checkout.ProductOrder
import kotlinx.coroutines.flow.Flow

interface ICheckoutOrderRepository {
    fun getCheckoutOrder(): Flow<CheckoutOrder?>
    fun getProductOrder(id: String): Flow<ProductOrder?>
    fun addToCart(product: Product, checkoutOrderId: Int)
    fun removeFromCart(product: Product, checkoutOrderId: Int)
    fun init() {}
}
