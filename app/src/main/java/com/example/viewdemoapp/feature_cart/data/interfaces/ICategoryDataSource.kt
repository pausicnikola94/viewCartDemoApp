package com.example.viewdemoapp.feature_cart.data.interfaces

import com.example.viewdemoapp.core.util.Resource
import com.example.viewdemoapp.feature_cart.data.remote.dto.ProductDto

interface ICategoryDataSource {
    suspend fun getCategories(): Resource<List<String>>
    suspend fun getProductsByCategoryId(id: String): Resource<List<ProductDto>>
}
