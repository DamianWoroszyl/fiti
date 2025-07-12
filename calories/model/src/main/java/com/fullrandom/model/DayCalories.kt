package com.fullrandom.model

import java.time.LocalDate

data class DayCalories(
    val date: LocalDate,
    val consumedProducts: List<ConsumedProduct>,
)