package com.fullrandom.calories.domain

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.model.ConsumedMeal
import com.fullrandom.model.DateRange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCaloriesUseCase @Inject constructor(
    private val caloriesRepository: CaloriesRepository
) {
    operator fun invoke(dateRange: DateRange): Flow<List<ConsumedMeal>> =
        caloriesRepository.observeConsumedCalories(dateRange)
}