package com.fullrandom.fiti.calories.storage.database

import com.fullrandom.fiti.calories.storage.database.dao.ConsumedProductDao
import com.fullrandom.fiti.calories.storage.database.dao.MealDao
import com.fullrandom.fiti.calories.storage.database.dao.ProductDao
import com.fullrandom.fiti.calories.storage.database.entity.ConsumedProductEntity
import com.fullrandom.fiti.calories.storage.database.entity.MealEntity
import com.fullrandom.fiti.calories.storage.database.entity.ProductEntity
import com.fullrandom.fiti.calories.storage.database.entity.toDomain
import com.fullrandom.fiti.storage.api.CaloriesStorage
import com.fullrandom.model.ConsumedProduct
import com.fullrandom.model.DateRange
import com.fullrandom.model.DayCalories
import com.fullrandom.model.Meal
import com.fullrandom.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CaloriesStorageImpl(
    private val consumedProductDao: ConsumedProductDao,
    private val productDao: ProductDao,
    private val mealDao: MealDao,
) : CaloriesStorage {

    override suspend fun saveProduct(product: Product) {
        productDao.insert(ProductEntity.fromDomain(product))
    }

    override suspend fun saveConsumedCalories(
        products: List<ConsumedProduct>
    ) {
        consumedProductDao.insert(
            consumedProducts = products.map { ConsumedProductEntity.fromDomain(it) }
        )
    }

    override fun observeConsumedCalories(
        range: DateRange
    ): Flow<List<DayCalories>> {
        return consumedProductDao.getByDateRange(range.start, range.end)
            .map { entities ->
                val products: List<Product> = entities.mapNotNull { it.productId }
                    .distinct()
                    .mapNotNull { productId ->
                        productDao.getById(productId)?.toDomain()
                    }

                val consumedProduct = entities.map {
                    val product = products.find { product -> product.id == it.productId }

                    ConsumedProduct(
                        id = it.id,
                        mealId = it.mealId,
                        productName = product?.name ?: it.productName,
                        order = it.order,
                        date = it.date,
                        amountGrams = it.amountGrams,
                        product = product,
                        carbohydrates = it.carbohydrates,
                        fat = it.fat,
                        protein = it.protein,
                        kcal = it.kcal,
                    )
                }

                val dates = entities.map { it.date }.distinct()

                dates.map {
                    DayCalories(
                        date = it,
                        consumedProducts = consumedProduct.filter { product -> product.date == it }
                    )
                }
            }
    }

    override fun observeAvailableProducts(): Flow<List<Product>> {
        return productDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun searchProduct(query: String): Flow<List<Product>> {
        return productDao.searchByName(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun observeMeals(): Flow<List<Meal>> {
        return mealDao.observeAll().map { mealEntityList ->
            mealEntityList.map { mealEntity ->
                mealEntity.toDomain()
            }
        }
    }

    override suspend fun saveMeal(meal: Meal) {
        mealDao.insert(MealEntity.fromDomain(meal))
    }

    override fun searchMeal(query: String): Flow<List<Meal>> {
        return mealDao.searchByName(query).map { mealEntityList ->
            mealEntityList.map { mealEntity ->
                mealEntity.toDomain()
            }
        }
    }
}