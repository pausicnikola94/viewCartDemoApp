package com.example.viewdemoapp.feature_cart.domain.use_cases

import com.example.viewdemoapp.core.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private lateinit var fakeCategoryRepository: FakeCategoryRepository
private lateinit var fakeCheckoutOrderRepository: FakeCheckoutOrderRepository
private lateinit var getCategoryProducts: GetCategoryProducts

class GetCategoryProductsTest {
    @Before
    fun createRepository() {
        fakeCategoryRepository = FakeCategoryRepository()
        fakeCheckoutOrderRepository = FakeCheckoutOrderRepository()

        getCategoryProducts = GetCategoryProducts(fakeCategoryRepository, fakeCheckoutOrderRepository)
    }

    @Test
    fun getCategoryProducts_categoryProductsReceived(): Unit = runBlocking {
        val result = getCategoryProducts.getCategoryProducts("electronics").first()
        assertThat(result.status).isEqualTo(Resource.Status.SUCCESS)
        assertThat(result.data?.count()).isEqualTo(1)
    }
}
