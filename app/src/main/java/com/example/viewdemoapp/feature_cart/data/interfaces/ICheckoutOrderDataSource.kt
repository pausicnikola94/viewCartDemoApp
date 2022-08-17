package com.example.viewdemoapp.feature_cart.data.interfaces

import com.example.viewdemoapp.feature_cart.data.local.entities.CheckoutOrderEntity
import kotlinx.coroutines.flow.Flow

interface ICheckoutOrderDataSource {
    fun getCheckoutOrder(): Flow<CheckoutOrderEntity?>
    fun insertOrUpdate(checkoutOrder: CheckoutOrderEntity)
    fun doesItemExist(id: Int): Boolean
}
