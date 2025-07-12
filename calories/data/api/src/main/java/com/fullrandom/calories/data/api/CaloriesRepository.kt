package com.fullrandom.calories.data.api

import com.fullrandom.model.ConsumedProduct
import com.fullrandom.model.DateRange
import com.fullrandom.model.DayCalories
import com.fullrandom.model.Meal
import com.fullrandom.model.Product
import kotlinx.coroutines.flow.Flow

interface CaloriesRepository {
    suspend fun saveProduct(product: Product)
    suspend fun saveConsumedCalories(products: List<ConsumedProduct>)
    fun observeConsumedCalories(range: DateRange): Flow<List<DayCalories>>
    fun observeAvailableProducts(): Flow<List<Product>>
    fun searchProduct(query: String): Flow<List<Product>>
    fun observeMeals(): Flow<List<Meal>>
    suspend fun saveMeal(meal: Meal)
    fun searchMeal(query: String): Flow<List<Meal>>
}