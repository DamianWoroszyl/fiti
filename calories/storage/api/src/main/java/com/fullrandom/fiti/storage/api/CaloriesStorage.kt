package com.fullrandom.fiti.storage.api

import com.fullrandom.model.ConsumedProduct
import com.fullrandom.model.DateRange
import com.fullrandom.model.Meal
import com.fullrandom.model.Product
import kotlinx.coroutines.flow.Flow

interface CaloriesStorage {

    suspend fun saveProduct(product: Product)
    suspend fun deleteProduct(id: String)
    suspend fun getProduct(id: String): Product?
    suspend fun saveConsumedCalories(mealId: String, products: List<ConsumedProduct>): Result<Unit>
    fun observeConsumedProducts(range: DateRange): Flow<List<ConsumedProduct>>
    fun observeMealToConsumedProductAssignments(range: DateRange): Flow<Map<String, List<String>>>
    fun observeAvailableProducts(): Flow<List<Product>>
    fun searchProduct(query: String, limit: Int): Flow<List<Product>>
    fun observeMeals(): Flow<List<Meal>>
    suspend fun saveMeal(meal: Meal)
    fun searchMeal(query: String): Flow<List<Meal>>
    fun observeMeal(id: String): Flow<Meal?>
    suspend fun getMeal(id: String): Meal?
}