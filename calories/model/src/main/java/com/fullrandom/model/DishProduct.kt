package com.fullrandom.model

data class DishProduct(
    val id: String,
    val dishId: String,
    val order: Int,
    val product: Product?,
    val amountGrams: Double,
)
