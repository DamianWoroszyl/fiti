package com.fullrandom.model

import java.time.LocalDate

data class ConsumedMeal(
    val meal: Meal,
    val date: LocalDate,
    val products: List<ConsumedProduct>,
    val dishes: List<ConsumedDish>,
)
