package com.fullrandom.calories.domain

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.model.Meal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMealsUseCase @Inject constructor(private val repo: CaloriesRepository) {
    operator fun invoke(): Flow<List<Meal>> = repo.observeMeals()
}
