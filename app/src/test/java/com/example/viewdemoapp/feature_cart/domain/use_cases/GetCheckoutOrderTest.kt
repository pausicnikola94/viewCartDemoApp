package com.example.viewdemoapp.feature_cart.domain.use_cases

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private lateinit var fakeCategoryRepository: FakeCategoryRepository
private lateinit var fakeCheckoutOrderRepository: FakeCheckoutOrderRepository
private lateinit var getCheckoutOrder: GetCheckoutOrder

class GetCheckoutOrderTest {
    @Before
    fun createRepository() {
        fakeCategoryRepository = FakeCategoryRepository()
        fakeCheckoutOrderRepository = FakeCheckoutOrderRepository()

        getCheckoutOrder = GetCheckoutOrder(fakeCategoryRepository, fakeCheckoutOrderRepository)
    }

    @Test
    fun getCheckoutOrder_checkoutOrderReceived() = runBlocking {
        val result = getCheckoutOrder.getCheckoutOrder().first()
        assertThat(result.data?.get(0)?.quantity).isEqualTo(1)
    }
}
