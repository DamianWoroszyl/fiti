package com.fullrandom.model

import java.time.LocalDate

data class PreConsumedProduct(
    val product: Product,
    val amountGrams: Int,
    val mealId: String,
    val date: LocalDate,
)