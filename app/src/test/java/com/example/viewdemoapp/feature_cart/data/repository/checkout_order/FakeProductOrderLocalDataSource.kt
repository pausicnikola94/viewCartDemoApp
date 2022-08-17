package com.example.viewdemoapp.feature_cart.data.repository.checkout_order

import com.example.viewdemoapp.feature_cart.data.interfaces.IProductOrderDataSource
import com.example.viewdemoapp.feature_cart.data.local.entities.ProductOrderEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeProductOrderLocalDataSource(var products: MutableList<ProductOrderEntity> = mutableListOf()) :
    IProductOrderDataSource {
    override fun getProductOrderByCheckoutOrderId(checkoutOrderId: Int): Flow<List<ProductOrderEntity>> = flow {
        emit(products.toList())
    }

    override fun getProductOrderById(productId: String): Flow<ProductOrderEntity?> = flow {
        emit(products.firstOrNull { it.id == productId })
    }

    override fun insertOrUpdate(productOrder: ProductOrderEntity) {
        val productIndex = products.indexOfFirst { it.id == productOrder.id }
        if (productIndex == -1) {
            products.add(productOrder)
        } else {
            products[productIndex] = productOrder
        }
    }

    override fun delete(id: String) {
        val toBeDeleted = products.firstOrNull { it.id == id }
        toBeDeleted?.let {
            products.remove(toBeDeleted)
        }
    }
}
