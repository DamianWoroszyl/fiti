package com.fullrandom.calories.domain

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(
    private val caloriesRepository: CaloriesRepository
) {
    operator fun invoke(query: String, limit: Int = 1000): Flow<List<Product>> =
        caloriesRepository.searchProduct(query, limit)
}