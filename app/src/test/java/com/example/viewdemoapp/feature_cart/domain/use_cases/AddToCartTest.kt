package com.example.viewdemoapp.feature_cart.domain.use_cases

import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.models.checkout.ProductOrder
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private lateinit var fakeCheckoutOrderRepository: FakeCheckoutOrderRepository
private lateinit var addToCart: AddToCart
class AddToCartTest {
    @Before
    fun createRepository() {
        fakeCheckoutOrderRepository = FakeCheckoutOrderRepository()
        addToCart = AddToCart(fakeCheckoutOrderRepository)
    }
    @Test
    fun addToCart_productAddedToCart() = runBlocking {
        addToCart.addProduct(
            Product(
                "shirt", "", "", "", 1
            ),
            0
        )
        val order = fakeCheckoutOrderRepository.getCheckoutOrder().first()
        assertThat(order?.products?.get(0)).isEqualTo(ProductOrder("shirt", 2))
    }
}
