package com.example.viewdemoapp.feature_cart.presentation.models

import android.os.Parcelable
import com.example.viewdemoapp.feature_cart.domain.models.checkout.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetails(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    var quantity: Int,
    val adapterPosition: Int
) : Parcelable

fun ProductDetails.toProduct(): Product {
    return Product(this.id, this.title, this.description, this.image, this.quantity)
}

fun Product.toProductDetails(): ProductDetails {
    return ProductDetails(this.id, this.title, this.description, this.image, this.quantity, 0)
}
