package com.fullrandom.model

import java.time.LocalDate

data class PreConsumedProduct(
    val product: Product,
    val amountGrams: Double,
    val mealId: String,
    val date: LocalDate,
    val order: Int,
)