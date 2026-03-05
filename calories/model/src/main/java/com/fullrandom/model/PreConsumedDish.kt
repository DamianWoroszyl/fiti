package com.fullrandom.model

import java.time.LocalDate

data class PreConsumedDish(
    val dishVariationId: String,
    val amountGrams: Int,
    val mealId: String,
    val date: LocalDate,
)