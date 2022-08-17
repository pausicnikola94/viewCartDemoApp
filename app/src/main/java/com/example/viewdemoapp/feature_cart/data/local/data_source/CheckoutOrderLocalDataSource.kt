package com.example.viewdemoapp.feature_cart.data.local.data_source

import com.example.viewdemoapp.feature_cart.data.interfaces.ICheckoutOrderDataSource
import com.example.viewdemoapp.feature_cart.data.local.dao.CheckoutOrderDao
import com.example.viewdemoapp.feature_cart.data.local.entities.CheckoutOrderEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckoutOrderLocalDataSource @Inject constructor(
    private val checkoutOrderDao: CheckoutOrderDao
) : ICheckoutOrderDataSource {
    override fun getCheckoutOrder(): Flow<CheckoutOrderEntity?> {
        return checkoutOrderDao.getCheckoutOrder()
    }

    override fun insertOrUpdate(checkoutOrder: CheckoutOrderEntity) {
        checkoutOrderDao.insertOrUpdate(checkoutOrder)
    }

    override fun doesItemExist(id: Int): Boolean {
        return checkoutOrderDao.doesItemExist(id)
    }
}
