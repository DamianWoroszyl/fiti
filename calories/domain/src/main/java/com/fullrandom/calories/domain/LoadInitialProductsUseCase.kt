package com.fullrandom.calories.domain

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.calories.data.api.ProductLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadInitialProductsUseCase @Inject constructor(
    private val repository: CaloriesRepository,
    private val productLoader: ProductLoader,
) {
    suspend operator fun invoke() {
        if (repository.isProductInitDone()) return
        withContext(Dispatchers.IO) { productLoader.loadProducts() }
        repository.markProductInitDone()
    }
}
