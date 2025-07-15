package com.fullrandom.calories.domain

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.model.ConsumedProduct
import javax.inject.Inject

class SaveConsumedCaloriesUseCase @Inject constructor(
    private val caloriesRepository: CaloriesRepository
) {
    suspend operator fun invoke(products: List<ConsumedProduct>) =
        caloriesRepository.saveConsumedCalories(products)
}