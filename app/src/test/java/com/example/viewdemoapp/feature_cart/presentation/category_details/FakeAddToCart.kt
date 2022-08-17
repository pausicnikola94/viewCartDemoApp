package com.example.viewdemoapp.feature_cart.presentation.category_details

import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IAddToCart

class FakeAddToCart : IAddToCart {
    override fun addProduct(product: Product, checkoutOrderId: Int) { }
}
