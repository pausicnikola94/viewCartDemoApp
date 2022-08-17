package com.example.viewdemoapp.feature_cart.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.viewdemoapp.feature_cart.data.local.AppDatabase
import com.example.viewdemoapp.feature_cart.data.local.entities.CheckoutOrderEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckoutOrderDaoTest {
    private lateinit var checkoutOrderDao: CheckoutOrderDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        checkoutOrderDao = db.checkoutOrderDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insert_checkoutOrderInserted() = runBlocking {
        val checkoutOrderEntity = CheckoutOrderEntity(0)
        checkoutOrderDao.insert(checkoutOrderEntity)
        val result = checkoutOrderDao.getCheckoutOrder().first()
        assertThat(result).isEqualTo(checkoutOrderEntity)
    }
}
