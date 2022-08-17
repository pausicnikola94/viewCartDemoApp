package com.example.viewdemoapp.feature_cart.presentation.category_details

import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IRemoveFromCart

class FakeRemoveFromCart : IRemoveFromCart {
    override fun removeProduct(product: Product, checkoutOrderId: Int) { }
}
