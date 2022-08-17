package com.example.viewdemoapp.feature_cart.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.viewdemoapp.feature_cart.domain.models.checkout.ProductOrder
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "productorder")
data class ProductOrderEntity(
    @PrimaryKey val id: String,
    val quantity: Int,
    val checkoutOrderId: Int
) : Parcelable {
    fun toProductOrder(): ProductOrder {
        return ProductOrder(this.id, this.quantity)
    }
}
