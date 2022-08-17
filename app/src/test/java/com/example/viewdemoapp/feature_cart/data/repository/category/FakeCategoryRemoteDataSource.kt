package com.example.viewdemoapp.feature_cart.data.repository.category

import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.data.interfaces.ICategoryDataSource
import com.example.viewdemoapp.feature_cart.data.remote.dto.ProductDto

class FakeCategoryRemoteDataSource : ICategoryDataSource {

    var categoriesResult: Resource<List<String>> = Resource.loading()
    var categoryProductsResult: Resource<List<ProductDto>> = Resource.loading()

    override suspend fun getCategories(): Resource<List<String>> {
        return categoriesResult
    }

    override suspend fun getProductsByCategoryId(id: String): Resource<List<ProductDto>> {
        if (categoriesResult.data?.contains(id) == true) {
            return categoryProductsResult
        } else {
            return Resource.error("Id not valid")
        }
    }

    fun setFakeValues(categoriesRes: Resource<List<String>>, categoryProductsRes: Resource<List<ProductDto>>) {
        categoriesResult = categoriesRes
        categoryProductsResult = categoryProductsRes
    }
}
