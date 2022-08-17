package com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces

import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product

interface IRemoveFromCart {
    fun removeProduct(product: Product, checkoutOrderId: Int)
}
