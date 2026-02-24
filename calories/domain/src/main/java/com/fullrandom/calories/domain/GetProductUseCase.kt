package com.fullrandom.calories.domain

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.model.Product
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repo: CaloriesRepository) {
    suspend operator fun invoke(id: String): Product? = repo.getProduct(id)
}
