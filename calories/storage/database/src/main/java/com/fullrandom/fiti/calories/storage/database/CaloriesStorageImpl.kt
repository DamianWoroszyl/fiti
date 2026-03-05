package com.fullrandom.fiti.calories.storage.database

import com.fullrandom.fiti.calories.storage.database.dao.ConsumedProductDao
import com.fullrandom.fiti.calories.storage.database.dao.MealDao
import com.fullrandom.fiti.calories.storage.database.dao.MealToConsumedProductDao
import com.fullrandom.fiti.calories.storage.database.dao.ProductDao
import com.fullrandom.fiti.calories.storage.database.entity.ConsumedProductEntity
import com.fullrandom.fiti.calories.storage.database.entity.MealEntity
import com.fullrandom.fiti.calories.storage.database.entity.MealToConsumedProductEntity
import com.fullrandom.fiti.calories.storage.database.entity.ProductEntity
import com.fullrandom.fiti.calories.storage.database.entity.toDomain
import com.fullrandom.fiti.storage.api.CaloriesStorage
import com.fullrandom.model.ConsumedProduct
import com.fullrandom.model.DateRange
import com.fullrandom.model.Meal
import com.fullrandom.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CaloriesStorageImpl(
    private val consumedProductDao: ConsumedProductDao,
    private val productDao: ProductDao,
    private val mealDao: MealDao,
    private val mealToConsumedProductDao: MealToConsumedProductDao,
) : CaloriesStorage {

    override suspend fun saveProduct(product: Product) {
        productDao.insert(ProductEntity.fromDomain(product))
    }

    override suspend fun deleteProduct(id: String) {
        productDao.delete(id)
    }

    override suspend fun getProduct(id: String): Product? {
        return productDao.getById(id)?.toDomain()
    }

    override suspend fun saveConsumedCalories(mealId: String, products: List<ConsumedProduct>): Result<Unit> {
        consumedProductDao.insert(products.map { ConsumedProductEntity.fromDomain(it) })
        mealToConsumedProductDao.insert(
            products.map { MealToConsumedProductEntity(mealId = mealId, productId = it.id) }
        )
        return Result.success(Unit)
    }

    override fun observeConsumedProducts(range: DateRange): Flow<List<ConsumedProduct>> {
        return consumedProductDao.getByDateRange(range.start, range.end)
            .map { consumedProductEntities: List<ConsumedProductEntity> ->
                val productById: Map<String, Product> = consumedProductEntities
                    .mapNotNull { consumedProductEntity: ConsumedProductEntity -> consumedProductEntity.productId }
                    .distinct()
                    .mapNotNull { productId: String -> productDao.getById(productId)?.toDomain() }
                    .associateBy { product: Product -> product.id }

                consumedProductEntities.map { consumedProductEntity: ConsumedProductEntity ->
                    val resolvedProduct: Product? = productById[consumedProductEntity.productId]
                    ConsumedProduct(
                        id = consumedProductEntity.id,
                        order = consumedProductEntity.order,
                        date = consumedProductEntity.date,
                        amountGrams = consumedProductEntity.amountGrams,
                        product = resolvedProduct,
                        productName = resolvedProduct?.name ?: consumedProductEntity.productName,
                        carbohydratesPer100g = resolvedProduct?.carbohydratesPer100g ?: consumedProductEntity.carbohydratesPer100g,
                        fatPer100g = resolvedProduct?.fatPer100g ?: consumedProductEntity.fatPer100g,
                        proteinPer100g = resolvedProduct?.proteinPer100g ?: consumedProductEntity.proteinPer100g,
                        kcalPer100g = resolvedProduct?.kcalPer100g ?: consumedProductEntity.kcalPer100g,
                    )
                }
            }
    }

    override fun observeMealToConsumedProductAssignments(range: DateRange): Flow<Map<String, List<String>>> {
        return mealToConsumedProductDao.observeByDateRange(range.start, range.end)
            .map { joinEntities: List<MealToConsumedProductEntity> ->
                joinEntities
                    .groupBy { joinEntity: MealToConsumedProductEntity -> joinEntity.mealId }
                    .mapValues { (_, joinEntitiesForMeal: List<MealToConsumedProductEntity>) ->
                        joinEntitiesForMeal.map { joinEntity: MealToConsumedProductEntity -> joinEntity.productId }
                    }
            }
    }

    override fun observeAvailableProducts(): Flow<List<Product>> {
        return productDao.getAll().map { productEntities: List<ProductEntity> ->
            productEntities.map { productEntity: ProductEntity -> productEntity.toDomain() }
        }
    }

    override fun searchProduct(query: String): Flow<List<Product>> {
        return productDao.searchByName(query).map { productEntities: List<ProductEntity> ->
            productEntities.map { productEntity: ProductEntity -> productEntity.toDomain() }
        }
    }

    override fun observeMeals(): Flow<List<Meal>> {
        return mealDao.observeAll().map { mealEntities: List<MealEntity> ->
            mealEntities.map { mealEntity: MealEntity -> mealEntity.toDomain() }
        }
    }

    override suspend fun saveMeal(meal: Meal) {
        mealDao.insert(MealEntity.fromDomain(meal))
    }

    override fun searchMeal(query: String): Flow<List<Meal>> {
        return mealDao.searchByName(query).map { mealEntities: List<MealEntity> ->
            mealEntities.map { mealEntity: MealEntity -> mealEntity.toDomain() }
        }
    }

    override fun observeMeal(id: String): Flow<Meal?> {
        return mealDao.getById(id).map { mealEntity: MealEntity? -> mealEntity?.toDomain() }
    }

    override suspend fun getMeal(id: String): Meal? {
        return mealDao.findById(id)?.toDomain()
    }
}
