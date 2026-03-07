package com.fullrandom.fiti.calories.storage.database.di

import com.fullrandom.fiti.calories.storage.database.CaloriesStorageImpl
import com.fullrandom.fiti.calories.storage.database.dao.ConsumedProductDao
import com.fullrandom.fiti.calories.storage.database.dao.MealDao
import com.fullrandom.fiti.calories.storage.database.dao.MealToConsumedProductDao
import com.fullrandom.fiti.calories.storage.database.dao.ProductDao
import com.fullrandom.fiti.storage.api.CaloriesStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object StorageModule {

    @Singleton
    @Provides
    internal fun provideStorage(
        consumedProductDao: ConsumedProductDao,
        mealDao: MealDao,
        mealToConsumedProductDao: MealToConsumedProductDao,
        productDao: ProductDao,
    ): CaloriesStorage {
        return CaloriesStorageImpl(
            consumedProductDao = consumedProductDao,
            mealDao = mealDao,
            mealToConsumedProductDao = mealToConsumedProductDao,
            productDao = productDao,
        )
    }

}