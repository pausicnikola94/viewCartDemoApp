package com.example.viewdemoapp.feature_cart.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.viewdemoapp.feature_cart.data.local.dao.CheckoutOrderDao
import com.example.viewdemoapp.feature_cart.data.local.dao.ProductOrderDao
import com.example.viewdemoapp.feature_cart.data.local.entities.CheckoutOrderEntity
import com.example.viewdemoapp.feature_cart.data.local.entities.ProductOrderEntity

@Database(entities = [CheckoutOrderEntity::class, ProductOrderEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun checkoutOrderDao(): CheckoutOrderDao
    abstract fun productOrderDao(): ProductOrderDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "viewDemoAppDB")
                .fallbackToDestructiveMigration()
                .build()
    }
}
