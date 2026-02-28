package com.fullrandom.model

data class DishVariation(
    val id: String,
    val baseDishId: String,
    val variationName: String,
    val products: List<DishProduct>
)