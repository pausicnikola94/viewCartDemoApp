package com.example.viewdemoapp.feature_cart.data.repository

import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.data.interfaces.ICategoryDataSource
import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import com.example.viewdemoapp.feature_cart.domain.models.main.Category
import com.example.viewdemoapp.feature_cart.domain.models.main.CategoryType
import com.example.viewdemoapp.feature_cart.domain.repository.ICategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryRemoteDataSource: ICategoryDataSource
) : ICategoryRepository {

    private var categories: Resource<List<Category>>? = null

    // TODO: Get Category
    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        if (categories == null) {
            categories = updateCategoriesCash()
        }
        emit(categories!!)
    }

    private suspend fun updateCategoriesCash(): Resource<List<Category>> {
        val remoteCategories = categoryRemoteDataSource.getCategories()
        if (remoteCategories.status == Resource.Status.SUCCESS) {
            if (remoteCategories.data != null) {
                val categories = mutableListOf<Category>()
                for (item in remoteCategories.data) {
                    val getProdResult = getProducts(item)
                    if (getProdResult.status == Resource.Status.ERROR) return Resource.error(getProdResult.message ?: "Get Product Failed")
                    if (getProdResult.status == Resource.Status.SUCCESS) {
                        if (getProdResult.data == null) break
                        categories.add(Category(item, getProdResult.data, getCategoryType(item)))
                    }
                }
                return Resource.success(categories.toList())
            }
        }
        return Resource.error(remoteCategories.message ?: "Get Category Failed")
    }

    private fun getCategoryType(id: String): CategoryType {
        return when (id) {
            "electronics" -> CategoryType.ELECTRONICS
            "jewelery" -> CategoryType.JEWELERY
            "men's clothing" -> CategoryType.MENSCLOTHING
            "women's clothing" -> CategoryType.WOMENSCLOTHING
            else -> CategoryType.NOTFOUND
        }
    }

    private suspend fun getProducts(categoryId: String): Resource<List<Product>> {
        val remoteProductsResult = categoryRemoteDataSource.getProductsByCategoryId(categoryId)
        if (remoteProductsResult.status == Resource.Status.SUCCESS) {
            if (remoteProductsResult.data != null) {
                val products = mutableListOf<Product>()
                for (item in remoteProductsResult.data) {
                    products.add(item.toProduct())
                }
                return Resource.success(products)
            }
        }
        return Resource.error("Get Product Failed")
    }
}
