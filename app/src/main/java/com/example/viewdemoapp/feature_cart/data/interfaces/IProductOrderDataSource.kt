package com.example.viewdemoapp.feature_cart.data.interfaces

import com.example.viewdemoapp.feature_cart.data.local.entities.ProductOrderEntity
import kotlinx.coroutines.flow.Flow

interface IProductOrderDataSource {
    fun getProductOrderByCheckoutOrderId(checkoutOrderId: Int): Flow<List<ProductOrderEntity>>
    fun getProductOrderById(productId: String): Flow<ProductOrderEntity?>
    fun insertOrUpdate(productOrder: ProductOrderEntity)
    fun delete(id: String)
}
