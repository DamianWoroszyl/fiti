package com.fullrandom.model

import java.time.LocalDate

data class ConsumedMeal(
    val mealId: String,
    val name: String,
    val order: Int,
    val date: LocalDate,
    val products: List<Product>,
)