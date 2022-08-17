package com.example.viewdemoapp.feature_cart.di

import com.example.viewdemoapp.core.Constants
import com.example.viewdemoapp.feature_cart.data.interfaces.ICategoryDataSource
import com.example.viewdemoapp.feature_cart.data.interfaces.ICheckoutOrderDataSource
import com.example.viewdemoapp.feature_cart.data.interfaces.IProductOrderDataSource
import com.example.viewdemoapp.feature_cart.data.local.AppDatabase
import com.example.viewdemoapp.feature_cart.data.local.dao.CheckoutOrderDao
import com.example.viewdemoapp.feature_cart.data.local.dao.ProductOrderDao
import com.example.viewdemoapp.feature_cart.data.local.data_source.CheckoutOrderLocalDataSource
import com.example.viewdemoapp.feature_cart.data.local.data_source.ProductOrderLocalDataSource
import com.example.viewdemoapp.feature_cart.data.remote.api.CategoryApi
import com.example.viewdemoapp.feature_cart.data.remote.data_source.CategoryRemoteDataSource
import com.example.viewdemoapp.feature_cart.data.repository.CategoryRepository
import com.example.viewdemoapp.feature_cart.data.repository.CheckoutOrderRepository
import com.example.viewdemoapp.feature_cart.domain.repository.ICategoryRepository
import com.example.viewdemoapp.feature_cart.domain.repository.ICheckoutOrderRepository
import com.example.viewdemoapp.feature_cart.domain.use_cases.AddToCart
import com.example.viewdemoapp.feature_cart.domain.use_cases.GetCategoryProducts
import com.example.viewdemoapp.feature_cart.domain.use_cases.GetCategories
import com.example.viewdemoapp.feature_cart.domain.use_cases.GetCheckoutOrder
import com.example.viewdemoapp.feature_cart.domain.use_cases.GetProduct
import com.example.viewdemoapp.feature_cart.domain.use_cases.RemoveFromCart
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IAddToCart
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IGetCategoryProducts
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IGetCategories
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IGetCheckoutOrder
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IGetProduct
import com.example.viewdemoapp.feature_cart.domain.use_cases.interfaces.IRemoveFromCart
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {

    @Singleton
    @Provides
    fun provideCheckoutOrderDao(db: AppDatabase) = db.checkoutOrderDao()

    @Singleton
    @Provides
    fun provideOrderProductDao(db: AppDatabase) = db.productOrderDao()

    @Singleton
    @Provides
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi = retrofit.create(
        CategoryApi::class.java
    )

    @Singleton
    @Provides
    fun provideCategoryRemoteDataSource(
        categoryApi: CategoryApi
    ) = CategoryRemoteDataSource(categoryApi) as ICategoryDataSource

    @Singleton
    @Provides
    fun provideCheckoutOrderDataSource(
        checkoutOrderDao: CheckoutOrderDao
    ) = CheckoutOrderLocalDataSource(checkoutOrderDao) as ICheckoutOrderDataSource

    @Singleton
    @Provides
    fun provideProductDataSource(
        productOrderDao: ProductOrderDao
    ) = ProductOrderLocalDataSource(productOrderDao) as IProductOrderDataSource

    @Singleton
    @Provides
    fun provideCategoryRepository(
        categoryRemoteDataSource: ICategoryDataSource
    ) = CategoryRepository(categoryRemoteDataSource) as ICategoryRepository

    @Singleton
    @Provides
    fun provideCheckoutOrderRepository(
        productOrderLocalDataSource: IProductOrderDataSource,
        checkoutOrderLocalDataSource: ICheckoutOrderDataSource
    ) = CheckoutOrderRepository(productOrderLocalDataSource, checkoutOrderLocalDataSource) as ICheckoutOrderRepository

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideGetCategoryProducts(categoryRepository: ICategoryRepository, checkoutOrderRepository: ICheckoutOrderRepository) =
        GetCategoryProducts(categoryRepository, checkoutOrderRepository) as IGetCategoryProducts

    @Singleton
    @Provides
    fun provideGetCheckoutOrder(categoryRepository: ICategoryRepository, checkoutOrderRepository: ICheckoutOrderRepository) =
        GetCheckoutOrder(categoryRepository, checkoutOrderRepository) as IGetCheckoutOrder

    @Singleton
    @Provides
    fun provideAddToCart(checkoutOrderRepository: ICheckoutOrderRepository) =
        AddToCart(checkoutOrderRepository) as IAddToCart

    @Singleton
    @Provides
    fun provideRemoveFromCart(checkoutOrderRepository: ICheckoutOrderRepository) =
        RemoveFromCart(checkoutOrderRepository) as IRemoveFromCart

    @Singleton
    @Provides
    fun provideGetCategorySection(categoryRepository: ICategoryRepository) =
        GetCategories(categoryRepository) as IGetCategories

    @Singleton
    @Provides
    fun provideGetProduct(checkoutOrderRepository: ICheckoutOrderRepository) =
        GetProduct(checkoutOrderRepository) as IGetProduct
}
