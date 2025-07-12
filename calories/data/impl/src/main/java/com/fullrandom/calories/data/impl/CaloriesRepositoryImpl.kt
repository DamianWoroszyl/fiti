package com.fullrandom.calories.data.impl

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.fiti.storage.api.CaloriesStorage
import com.fullrandom.model.ConsumedProduct
import com.fullrandom.model.DateRange
import com.fullrandom.model.DayCalories
import com.fullrandom.model.Meal
import com.fullrandom.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CaloriesRepositoryImpl @Inject constructor(
    private val caloriesStorage: CaloriesStorage
) : CaloriesRepository {

    override suspend fun saveProduct(product: Product) {
        caloriesStorage.saveProduct(product)
    }

    override suspend fun saveConsumedCalories(products: List<ConsumedProduct>) {
        caloriesStorage.saveConsumedCalories(products)
    }

    override fun observeConsumedCalories(range: DateRange): Flow<List<DayCalories>> {
        return caloriesStorage.observeConsumedCalories(range)
    }

    override fun observeAvailableProducts(): Flow<List<Product>> {
        return caloriesStorage.observeAvailableProducts()
    }

    override fun searchProduct(query: String): Flow<List<Product>> {
        return caloriesStorage.searchProduct(query)
    }

    override fun observeMeals(): Flow<List<Meal>> {
        return caloriesStorage.observeMeals()
    }

    override suspend fun saveMeal(meal: Meal) {
        caloriesStorage.saveMeal(meal)
    }

    override fun searchMeal(query: String): Flow<List<Meal>> {
        return caloriesStorage.searchMeal(query)
    }
}