package com.fullrandom.model

data class ConsumedDish(
    val dishId: String,
    val variationId: String?,
    val products: List<ConsumedProduct>
)
