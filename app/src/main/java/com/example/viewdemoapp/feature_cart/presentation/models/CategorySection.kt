package com.example.viewdemoapp.feature_cart.presentation.models

sealed class CategorySection(
    val id: String? = null,
    var products: List<ProductWrapper>? = null,
) {
    class CategorySectionClothing(id: String, products: List<ProductWrapper>) :
        CategorySection(id, products)
    class CategorySectionJewelery(id: String, products: List<ProductWrapper>) :
        CategorySection(id, products)
    class CategorySectionElectronics(id: String, products: List<ProductWrapper>) :
        CategorySection(id, products)
    class CategorySectionName(id: String) :
        CategorySection(id)
}
