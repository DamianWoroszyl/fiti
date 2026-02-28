package com.fullrandom.calories.data.impl

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.fiti.storage.api.CaloriesStorage
import com.fullrandom.model.ConsumedProduct
import com.fullrandom.model.DateRange
import com.fullrandom.model.DayCalories
import com.fullrandom.model.Meal
import com.fullrandom.model.PreConsumedProduct
import com.fullrandom.model.Product
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class CaloriesRepositoryImpl @Inject constructor(
    private val caloriesStorage: CaloriesStorage
) : CaloriesRepository {

    override suspend fun saveProduct(product: Product) {
        val productToSave = if (product.id.isBlank()) {
            product.copy(id = UUID.randomUUID().toString())
        } else {
            product
        }
        caloriesStorage.saveProduct(productToSave)
    }

    override suspend fun deleteProduct(id: String) {
        caloriesStorage.deleteProduct(id)
    }

    override suspend fun getProduct(id: String): Product? {
        return caloriesStorage.getProduct(id)
    }

    override suspend fun saveConsumedCalories(products: List<PreConsumedProduct>) {
        caloriesStorage.saveConsumedCalories(
            products.map{ preConsumedProduct ->
                ConsumedProduct(
                    id = UUID.randomUUID().toString(),
                    mealId = preConsumedProduct.mealId,
                    order = preConsumedProduct.order,
                    date = preConsumedProduct.date,
                    product = preConsumedProduct.product,
                    amountGrams = preConsumedProduct.amountGrams,
                    productName = preConsumedProduct.product.name,
                    carbohydratesPer100g = preConsumedProduct.product.carbohydratesPer100g,
                    fatPer100g = preConsumedProduct.product.fatPer100g,
                    proteinPer100g = preConsumedProduct.product.proteinPer100g,
                    kcalPer100g = preConsumedProduct.product.kcalPer100g,
                )
            }
        )
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