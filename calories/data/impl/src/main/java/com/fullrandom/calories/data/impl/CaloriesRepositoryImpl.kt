package com.fullrandom.calories.data.impl

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.fiti.storage.api.CaloriesStorage
import com.fullrandom.model.ConsumedMeal
import com.fullrandom.model.ConsumedProduct
import com.fullrandom.model.DateRange
import com.fullrandom.model.Meal
import com.fullrandom.model.PreConsumedProduct
import com.fullrandom.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
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

    override suspend fun saveConsumedCalories(mealId: String, products: List<PreConsumedProduct>): Result<Unit> {
        return caloriesStorage.saveConsumedCalories(
            mealId = mealId,
            products = products.map { product: PreConsumedProduct ->
                ConsumedProduct(
                    id = UUID.randomUUID().toString(),
                    order = product.order,
                    date = product.date,
                    product = product.product,
                    amountGrams = product.amountGrams,
                    productName = product.product.name,
                    carbohydratesPer100g = product.product.carbohydratesPer100g,
                    fatPer100g = product.product.fatPer100g,
                    proteinPer100g = product.product.proteinPer100g,
                    kcalPer100g = product.product.kcalPer100g,
                )
            }
        )
    }

    override fun observeConsumedCalories(range: DateRange): Flow<List<ConsumedMeal>> {
        return combine(
            caloriesStorage.observeConsumedProducts(range),
            caloriesStorage.observeMeals(),
            caloriesStorage.observeMealToConsumedProductAssignments(range),
        ) { consumedProductsInRange: List<ConsumedProduct>, allMeals: List<Meal>, consumedProductIdsByMealId: Map<String, List<String>> ->
            val consumedProductById: Map<String, ConsumedProduct> =
                consumedProductsInRange.associateBy { consumedProduct: ConsumedProduct -> consumedProduct.id }
            val mealById: Map<String, Meal> =
                allMeals.associateBy { meal: Meal -> meal.id }

            consumedProductIdsByMealId.entries.mapNotNull { (mealId: String, consumedProductIds: List<String>) ->
                val meal: Meal = mealById[mealId] ?: return@mapNotNull null
                val consumedProductsForMeal: List<ConsumedProduct> = consumedProductIds
                    .mapNotNull { consumedProductId: String -> consumedProductById[consumedProductId] }
                val date: LocalDate = consumedProductsForMeal.firstOrNull()?.date ?: return@mapNotNull null
                ConsumedMeal(
                    meal = meal,
                    date = date,
                    products = consumedProductsForMeal,
                    dishes = emptyList()
                )
            }
        }
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

    override fun observeMeal(id: String): Flow<Meal?> {
        return caloriesStorage.observeMeal(id)
    }

    override suspend fun getMeal(id: String): Meal? {
        return caloriesStorage.getMeal(id)
    }
}
