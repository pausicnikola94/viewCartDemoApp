package com.example.viewdemoapp.feature_cart.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.viewdemoapp.feature_cart.data.local.entities.CheckoutOrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckoutOrderDao {
    @Query("SELECT * FROM checkoutorder")
    fun getCheckoutOrder(): Flow<CheckoutOrderEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(checkoutOrder: CheckoutOrderEntity): Long

    @Update
    fun update(checkoutOrder: CheckoutOrderEntity)

    @Transaction
    fun insertOrUpdate(checkoutOrder: CheckoutOrderEntity) {
        val exist = doesItemExist(checkoutOrder.id)
        if (exist) {
            update(checkoutOrder)
        } else insert(checkoutOrder)
    }

    @Query("SELECT 1 FROM checkoutorder WHERE id == :id")
    fun doesItemExist(id: Int): Boolean
}
