package com.example.viewdemoapp.feature_cart.data.remote.api

import com.example.viewdemoapp.feature_cart.data.remote.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {
    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>

    @GET("products/category/{id}")
    suspend fun getProductsByCategoryId(@Path("id") id: String): Response<List<ProductDto>>
}
