package com.example.viewdemoapp.feature_cart.presentation.models

sealed class ProductWrapper(val product: ProductDetails) {
    class ProductMain(product: ProductDetails) : ProductWrapper(product)
    class ProductCart(product: ProductDetails) : ProductWrapper(product)
    class ProductCloathing(product: ProductDetails) : ProductWrapper(product)
    class ProductElectronics(product: ProductDetails) : ProductWrapper(product)
    class ProductJewlery(product: ProductDetails) : ProductWrapper(product)
}
