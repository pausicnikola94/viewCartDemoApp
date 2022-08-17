package com.example.viewdemoapp.feature_cart.data.repository

import com.example.viewdemoapp.feature_cart.data.interfaces.ICheckoutOrderDataSource
import com.example.viewdemoapp.feature_cart.data.interfaces.IProductOrderDataSource
import com.example.viewdemoapp.feature_cart.data.local.entities.CheckoutOrderEntity
import com.example.viewdemoapp.feature_cart.data.local.entities.ProductOrderEntity
import com.example.viewdemoapp.feature_cart.domain.models.checkout.CheckoutOrder
import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.models.checkout.ProductOrder
import com.example.viewdemoapp.feature_cart.domain.repository.ICheckoutOrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class CheckoutOrderRepository @Inject constructor(
    private val productOrderLocalDataSource: IProductOrderDataSource,
    private val checkoutOrderLocalDataSource: ICheckoutOrderDataSource
) : ICheckoutOrderRepository {

    override fun getCheckoutOrder(): Flow<CheckoutOrder?> {
        val initialCheckoutOrder = checkoutOrderLocalDataSource.getCheckoutOrder()
        val initialProductOrderList = productOrderLocalDataSource.getProductOrderByCheckoutOrderId(0)

        return initialCheckoutOrder.combine(initialProductOrderList) { order, products ->
            val productsMapped = products.map { it.toProductOrder() }
            order?.toCheckoutOrder(productsMapped)
        }
    }

    override fun getProductOrder(id: String): Flow<ProductOrder?> {
        return productOrderLocalDataSource.getProductOrderById(id).transform {
            emit(it?.toProductOrder())
        }
    }

    override fun addToCart(product: Product, checkoutOrderId: Int) {
        productOrderLocalDataSource.insertOrUpdate(ProductOrderEntity(product.id, product.quantity, checkoutOrderId))
    }

    override fun removeFromCart(product: Product, checkoutOrderId: Int) {
        if (product.quantity == 0) {
            productOrderLocalDataSource.delete(product.id)
        } else {
            productOrderLocalDataSource.insertOrUpdate(ProductOrderEntity(product.id, product.quantity, checkoutOrderId))
        }
    }

    override fun init() {
        if (!checkoutOrderLocalDataSource.doesItemExist(0)) {
            checkoutOrderLocalDataSource.insertOrUpdate(CheckoutOrderEntity(0))
        }
    }
}
