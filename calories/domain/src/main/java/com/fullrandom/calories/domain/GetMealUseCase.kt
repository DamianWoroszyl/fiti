package com.fullrandom.calories.domain

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.model.Meal
import javax.inject.Inject

class GetMealUseCase @Inject constructor(private val repo: CaloriesRepository) {
    suspend operator fun invoke(id: String): Meal? = repo.getMeal(id)
}
