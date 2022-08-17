package com.example.viewdemoapp.feature_cart.domain.use_cases

import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.domain.models.checkout.CheckoutOrder
import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.models.main.Category
import com.example.viewdemoapp.feature_cart.domain.repository.ICategoryRepository
import com.example.viewdemoapp.feature_cart.domain.repository.ICheckoutOrderRepository
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IGetCheckoutOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCheckoutOrder @Inject constructor(
    private val categoryRepository: ICategoryRepository,
    private val checkoutOrderRepository: ICheckoutOrderRepository
) : IGetCheckoutOrder {

    override suspend fun getCheckoutOrder(): Flow<Resource<List<Product>>> {
        val flowCategories = categoryRepository.getCategories()
        val flowCheckoutOrder = checkoutOrderRepository.getCheckoutOrder()

        return flowCategories.combine(flowCheckoutOrder) { categories, checkoutOrder ->
            createShoppingCartUiState(categories, checkoutOrder)
        }
    }

    private fun createShoppingCartUiState(
        result: Resource<List<Category>>,
        checkoutOrder: CheckoutOrder?
    ): Resource<List<Product>> {
        when (result.status) {
            Resource.Status.SUCCESS -> {
                if (result.data == null) return Resource.error(result.message ?: "No data")
                val products = mutableListOf<Product>()
                val categories = result.data
                val checkoutProducts = checkoutOrder?.products

                categories.forEach { category ->
                    category.products.forEach { product ->
                        val res = checkoutProducts?.firstOrNull() { checkoutProd ->
                            product.id == checkoutProd.id
                        }
                        res?.let {
                            products.add(
                                Product(
                                    product.id,
                                    product.title,
                                    product.description, product.image,
                                    res.quantity
                                )
                            )
                        }
                    }
                }
                return Resource.success(products)
            }
            Resource.Status.ERROR -> {
                return Resource.error(result.message ?: "")
            }
            Resource.Status.LOADING -> {
                return Resource.loading()
            }
        }
    }

    override suspend fun init() {
        checkoutOrderRepository.init()
    }
}
