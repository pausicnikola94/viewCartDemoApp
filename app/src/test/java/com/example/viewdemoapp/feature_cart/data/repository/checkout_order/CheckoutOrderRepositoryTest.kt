package com.example.viewdemoapp.feature_cart.data.repository.checkout_order

import com.example.viewdemoapp.feature_cart.data.repository.CheckoutOrderRepository
import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.models.checkout.ProductOrder
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CheckoutOrderRepositoryTest {
    private lateinit var checkoutOrderLocalDataSource: FakeCheckoutOrderLocalDataSource
    private lateinit var productOrderLocalDataSource: FakeProductOrderLocalDataSource
    private lateinit var checkoutOrderRepository: CheckoutOrderRepository
    private lateinit var baseProduct: Product

    @Before
    fun createRepository() {
        checkoutOrderLocalDataSource = FakeCheckoutOrderLocalDataSource()
        productOrderLocalDataSource = FakeProductOrderLocalDataSource()
        checkoutOrderRepository = CheckoutOrderRepository(
            productOrderLocalDataSource,
            checkoutOrderLocalDataSource
        )

        baseProduct = Product("shirt", "", "", "", 0)
    }

    @Test
    fun addToCart_productAddedToCart() = runBlocking {
        val product: ProductOrder?
        checkoutOrderRepository.addToCart(baseProduct.copy(quantity = 1), 0)
        val result = checkoutOrderRepository.getCheckoutOrder().first()
        product = result?.products?.firstOrNull { it.id == "shirt" }
        assertThat(product?.id).isEqualTo("shirt")
    }

    @Test
    fun removeFromCart_quantityDecreased() = runBlocking {
        checkoutOrderRepository.addToCart(baseProduct.copy(quantity = 2), 0)
        checkoutOrderRepository.removeFromCart(baseProduct.copy(quantity = 1), 0)
        val result = checkoutOrderRepository.getCheckoutOrder().first()
        val product = result?.products?.firstOrNull { it.id == "shirt" }
        assertThat(product?.quantity).isEqualTo(1)
    }

    @Test
    fun removeFromCart_productRemoved() = runBlocking {
        checkoutOrderRepository.addToCart(baseProduct.copy(quantity = 1), 0)
        checkoutOrderRepository.removeFromCart(baseProduct.copy(quantity = 0), 0)
        val result = checkoutOrderRepository.getCheckoutOrder().first()
        val product = result?.products?.firstOrNull { it.id == "shirt" }
        assertThat(product).isEqualTo(null)
    }

    @Test
    fun getProductOrder_productOrderReceived() = runBlocking {
        checkoutOrderRepository.addToCart(baseProduct.copy(quantity = 1), 0)
        val result = checkoutOrderRepository.getProductOrder(baseProduct.id).first()
        assertThat(result).isEqualTo(ProductOrder(baseProduct.id, 1))
    }

    @Test
    fun init_defaultCheckoutInserted() = runBlocking {
        checkoutOrderRepository.init()
        val result = checkoutOrderRepository.getCheckoutOrder().first()
        assertThat(result?.products).isEqualTo(listOf<Product>())
    }
}
