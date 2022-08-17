package com.example.viewdemoapp.feature_cart.data.local.data_source

import com.example.viewdemoapp.feature_cart.data.interfaces.IProductOrderDataSource
import com.example.viewdemoapp.feature_cart.data.local.dao.ProductOrderDao
import com.example.viewdemoapp.feature_cart.data.local.entities.ProductOrderEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductOrderLocalDataSource @Inject constructor(
    private val productOrderDao: ProductOrderDao
) : IProductOrderDataSource {
    override fun getProductOrderByCheckoutOrderId(checkoutOrderId: Int): Flow<List<ProductOrderEntity>> {
        return productOrderDao.getProductOrderByCheckoutOrderId(checkoutOrderId)
    }

    override fun getProductOrderById(productId: String): Flow<ProductOrderEntity?> {
        return productOrderDao.getProductOrderById(productId)
    }

    override fun insertOrUpdate(productOrder: ProductOrderEntity) {
        productOrderDao.insertOrUpdate(productOrder)
    }

    override fun delete(id: String) {
        productOrderDao.delete(id)
    }
}
