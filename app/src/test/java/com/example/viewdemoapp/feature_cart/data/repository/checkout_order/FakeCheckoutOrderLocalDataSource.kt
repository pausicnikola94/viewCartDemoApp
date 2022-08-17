package com.example.viewdemoapp.feature_cart.data.repository.checkout_order

import com.example.viewdemoapp.feature_cart.data.interfaces.ICheckoutOrderDataSource
import com.example.viewdemoapp.feature_cart.data.local.entities.CheckoutOrderEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCheckoutOrderLocalDataSource(var checkoutOrderEntity: CheckoutOrderEntity = CheckoutOrderEntity()) :
    ICheckoutOrderDataSource {
    override fun getCheckoutOrder(): Flow<CheckoutOrderEntity?> = flow {
        emit(checkoutOrderEntity)
    }

    override fun insertOrUpdate(checkoutOrder: CheckoutOrderEntity) {
        checkoutOrderEntity = checkoutOrder
    }

    override fun doesItemExist(id: Int): Boolean {
        return true
    }
}
