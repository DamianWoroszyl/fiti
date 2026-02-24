package com.fullrandom.calories.domain

import com.fullrandom.calories.data.api.CaloriesRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(private val repo: CaloriesRepository) {
    suspend operator fun invoke(id: String) = repo.deleteProduct(id)
}
