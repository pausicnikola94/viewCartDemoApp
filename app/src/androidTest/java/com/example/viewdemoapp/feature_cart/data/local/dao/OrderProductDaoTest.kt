package com.example.viewdemoapp.feature_cart.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.viewdemoapp.feature_cart.data.local.AppDatabase
import com.example.viewdemoapp.feature_cart.data.local.entities.ProductOrderEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrderProductDaoTest {
    private lateinit var productOrderDao: ProductOrderDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        productOrderDao = db.productOrderDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insert_productOrderInserted() = runBlocking {
        val productOrderEntity = ProductOrderEntity("shirt", 0, 0)
        productOrderDao.insert(productOrderEntity)

        val result = productOrderDao.getProductOrderByCheckoutOrderId(0).first()
        assertThat(result).isEqualTo(listOf(productOrderEntity))
    }

    @Test
    fun delete_productOrderDeleted() = runBlocking {
        val productOrderEntity = ProductOrderEntity("shirt", 0, 0)
        productOrderDao.insert(productOrderEntity)
        productOrderDao.delete("shirt")

        val result = productOrderDao.getProductOrderByCheckoutOrderId(0).first()
        assertThat(result).isEqualTo(listOf<ProductOrderEntity>())
    }

    @Test
    fun getProductOrderByCheckoutOrderId_productOrderReceived() = runBlocking {
        val productOrderEntity = ProductOrderEntity("shirt", 0, 0)
        productOrderDao.insert(productOrderEntity)

        val result = productOrderDao.getProductOrderByCheckoutOrderId(0).first()
        assertThat(result).isEqualTo(listOf(productOrderEntity))
    }

    @Test
    fun getProductOrderById_productOrderReceived() = runBlocking {
        val productOrderEntity = ProductOrderEntity("shirt", 0, 0)
        productOrderDao.insert(productOrderEntity)

        val result = productOrderDao.getProductOrderById("shirt").first()
        assertThat(result).isEqualTo(productOrderEntity)
    }
}
