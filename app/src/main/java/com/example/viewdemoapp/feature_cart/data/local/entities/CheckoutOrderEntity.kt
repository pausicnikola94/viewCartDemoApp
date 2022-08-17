package com.example.viewdemoapp.feature_cart.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.viewdemoapp.feature_cart.domain.models.checkout.CheckoutOrder
import com.example.viewdemoapp.feature_cart.domain.models.checkout.ProductOrder
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "checkoutorder")
data class CheckoutOrderEntity(
    @PrimaryKey val id: Int = 0
) : Parcelable {
    fun toCheckoutOrder(products: List<ProductOrder>): CheckoutOrder {
        return CheckoutOrder(this.id, products)
    }
}
