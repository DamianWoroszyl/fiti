package com.fullrandom.fiti.calories.storage.database.di

import com.fullrandom.fiti.calories.storage.database.CaloriesStorageImpl
import com.fullrandom.fiti.calories.storage.database.dao.MealToConsumedProductDao
import com.fullrandom.fiti.calories.storage.database.db.CaloriesDatabase
import com.fullrandom.fiti.storage.api.CaloriesStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object StorageModule {

    @Provides
    internal fun provideStorage(
        caloriesDatabase: CaloriesDatabase,
        mealToConsumedProductDao: MealToConsumedProductDao,
    ): CaloriesStorage {
        return CaloriesStorageImpl(
            consumedProductDao = caloriesDatabase.consumedProductDao(),
            mealDao = caloriesDatabase.mealDao(),
            mealToConsumedProductDao = mealToConsumedProductDao,
            productDao = caloriesDatabase.productDao()
        )
    }

}