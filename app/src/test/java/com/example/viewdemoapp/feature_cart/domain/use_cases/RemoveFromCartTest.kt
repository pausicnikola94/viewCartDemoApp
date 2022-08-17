package com.example.viewdemoapp.feature_cart.domain.use_cases

import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private lateinit var fakeCheckoutOrderRepository: FakeCheckoutOrderRepository
private lateinit var removeFromCart: RemoveFromCart

class RemoveFromCartTest {
    @Before
    fun createRepository() {
        fakeCheckoutOrderRepository = FakeCheckoutOrderRepository()
        removeFromCart = RemoveFromCart(fakeCheckoutOrderRepository)
    }

    @Test
    fun removeFromCart_productRemovedFromCart() = runBlocking {
        removeFromCart.removeProduct(
            Product(
                "shirt", "", "", "", 1
            ),
            0
        )
        val order = fakeCheckoutOrderRepository.getCheckoutOrder().first()
        assertThat(order?.products?.count()).isEqualTo(0)
    }
}
