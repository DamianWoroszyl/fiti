package com.fullrandom.fiti.calories.storage.database.di

import android.content.Context
import com.fullrandom.fiti.calories.storage.database.dao.ConsumedProductDao
import com.fullrandom.fiti.calories.storage.database.dao.MealDao
import com.fullrandom.fiti.calories.storage.database.dao.ProductDao
import com.fullrandom.fiti.calories.storage.database.db.CaloriesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    internal fun provideCaloriesDatabase(
        @ApplicationContext context: Context
    ): CaloriesDatabase {
        return CaloriesDatabase.getInstance(context)
    }

    @Provides
    internal fun provideConsumedProductDao(
        caloriesDatabase: CaloriesDatabase
    ): ConsumedProductDao {
        return caloriesDatabase.consumedProductDao()
    }

    @Provides
    internal fun provideMealDao(
        caloriesDatabase: CaloriesDatabase
    ): MealDao {
        return caloriesDatabase.mealDao()
    }

    @Provides
    internal fun provideProductDao(
        caloriesDatabase: CaloriesDatabase
    ): ProductDao {
        return caloriesDatabase.productDao()
    }
}