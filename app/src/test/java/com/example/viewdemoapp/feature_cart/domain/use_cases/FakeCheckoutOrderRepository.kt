package com.example.viewdemoapp.feature_cart.domain.use_cases

import com.example.viewdemoapp.feature_cart.domain.models.checkout.CheckoutOrder
import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.models.checkout.ProductOrder
import com.example.viewdemoapp.feature_cart.domain.repository.ICheckoutOrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCheckoutOrderRepository : ICheckoutOrderRepository {
    private var checkoutOrder: CheckoutOrder = CheckoutOrder(0, listOf(ProductOrder("shirt", 1)))

    override fun getCheckoutOrder(): Flow<CheckoutOrder?> = flow {
        emit(checkoutOrder)
    }

    override fun getProductOrder(id: String): Flow<ProductOrder?> = flow {
        emit(checkoutOrder.products.firstOrNull { it.id == id })
    }

    override fun addToCart(product: Product, checkoutOrderId: Int) {
        val productOrder = ProductOrder(product.id, product.quantity)

        val productIndex = checkoutOrder.products.indexOfFirst { it.id == productOrder.id }
        val productList = checkoutOrder.products.toMutableList()
        if (productIndex == -1) {
            productList.add(productOrder)
        } else {
            productList[productIndex] = productOrder
        }
        checkoutOrder = checkoutOrder.copy(products = productList)
    }

    override fun removeFromCart(product: Product, checkoutOrderId: Int) {
        val productOrder = ProductOrder(product.id, product.quantity)
        val productIndex = checkoutOrder.products.indexOfFirst { it.id == productOrder.id }
        val productList = checkoutOrder.products.toMutableList()
        if (productOrder.quantity == 0) {
            productList.removeIf { it.id == productOrder.id }
        } else {
            productList[productIndex] = productOrder.copy(quantity = productOrder.quantity)
        }
        checkoutOrder = checkoutOrder.copy(products = productList)
    }
}
