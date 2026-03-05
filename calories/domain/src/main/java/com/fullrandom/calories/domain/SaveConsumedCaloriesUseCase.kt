package com.fullrandom.calories.domain

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.model.PreConsumedProduct
import javax.inject.Inject

class SaveConsumedCaloriesUseCase @Inject constructor(
    private val caloriesRepository: CaloriesRepository
) {
    suspend operator fun invoke(mealId: String, products: List<PreConsumedProduct>) =
        caloriesRepository.saveConsumedCalories(mealId = mealId, products = products)
}