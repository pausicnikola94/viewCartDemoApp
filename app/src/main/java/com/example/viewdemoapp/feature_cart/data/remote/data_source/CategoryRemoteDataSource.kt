package com.example.viewdemoapp.feature_cart.data.remote.data_source

import com.example.viewdemoapp.feature_cart.data.interfaces.ICategoryDataSource
import com.example.viewdemoapp.feature_cart.data.remote.api.CategoryApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRemoteDataSource @Inject constructor(
    private val categoryApi: CategoryApi
) : ICategoryDataSource, BaseDataSource() {

    override suspend fun getCategories() = getResult { categoryApi.getCategories() }

    override suspend fun getProductsByCategoryId(id: String) = getResult { categoryApi.getProductsByCategoryId(id) }
}
