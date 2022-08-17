package com.example.viewdemoapp.feature_cart.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.viewdemoapp.feature_cart.data.local.entities.ProductOrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductOrderDao {
    @Query("SELECT * FROM productorder WHERE checkoutOrderId==:checkoutOrderId")
    fun getProductOrderByCheckoutOrderId(checkoutOrderId: Int): Flow<List<ProductOrderEntity>>

    @Query("SELECT * FROM productorder WHERE id == :id")
    fun getProductOrderById(id: String): Flow<ProductOrderEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productOrder: ProductOrderEntity): Long

    @Update
    fun update(productOrder: ProductOrderEntity)

    @Transaction
    fun insertOrUpdate(productOrder: ProductOrderEntity) {
        val exist = doesItemExist(productOrder.id)
        if (exist) {
            update(productOrder)
        } else insert(productOrder)
    }

    @Query("SELECT 1 FROM productorder WHERE id == :id")
    fun doesItemExist(id: String): Boolean

    @Query("DELETE FROM productorder WHERE id = :id")
    fun delete(id: String)
}
