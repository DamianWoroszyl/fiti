package com.fullrandom.calories.data.api

import com.fullrandom.model.ConsumedMeal
import com.fullrandom.model.DateRange
import com.fullrandom.model.Meal
import com.fullrandom.model.PreConsumedProduct
import com.fullrandom.model.Product
import kotlinx.coroutines.flow.Flow

interface CaloriesRepository {

    suspend fun saveProduct(product: Product)
    suspend fun deleteProduct(id: String)
    suspend fun getProduct(id: String): Product?
    fun searchProduct(query: String, limit: Int): Flow<List<Product>>

    suspend fun saveConsumedCalories(mealId: String, products: List<PreConsumedProduct>): Result<Unit>
    fun observeConsumedCalories(range: DateRange): Flow<List<ConsumedMeal>>

    fun observeAvailableProducts(): Flow<List<Product>>

    fun observeMeals(): Flow<List<Meal>>
    suspend fun saveMeal(meal: Meal)
    fun searchMeal(query: String): Flow<List<Meal>>
    fun observeMeal(id: String): Flow<Meal?>
    suspend fun getMeal(id: String): Meal?
}