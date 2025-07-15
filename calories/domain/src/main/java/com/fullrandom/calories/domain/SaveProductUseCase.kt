package com.fullrandom.calories.domain

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.model.Product
import javax.inject.Inject

class SaveProductUseCase @Inject constructor(
    private val caloriesRepository: CaloriesRepository
) {
    suspend operator fun invoke(product: Product) = caloriesRepository.saveProduct(product)
}