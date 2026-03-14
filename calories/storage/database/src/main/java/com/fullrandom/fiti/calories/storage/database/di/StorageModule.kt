package com.fullrandom.fiti.calories.storage.database.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.fullrandom.fiti.calories.storage.database.CaloriesStorageImpl
import com.fullrandom.fiti.calories.storage.database.dao.ConsumedProductDao
import com.fullrandom.fiti.calories.storage.database.dao.MealDao
import com.fullrandom.fiti.calories.storage.database.dao.MealToConsumedProductDao
import com.fullrandom.fiti.calories.storage.database.dao.ProductDao
import com.fullrandom.fiti.storage.api.CaloriesStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

private val Context.caloriesPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "calories_prefs"
)

@InstallIn(SingletonComponent::class)
@Module
object StorageModule {

    @Singleton
    @Provides
    @Named("calories_prefs")
    internal fun provideCaloriesPreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.caloriesPreferencesDataStore
    }

    @Singleton
    @Provides
    internal fun provideStorage(
        consumedProductDao: ConsumedProductDao,
        mealDao: MealDao,
        mealToConsumedProductDao: MealToConsumedProductDao,
        productDao: ProductDao,
        @Named("calories_prefs") caloriesPreferences: DataStore<Preferences>,
    ): CaloriesStorage {
        return CaloriesStorageImpl(
            consumedProductDao = consumedProductDao,
            mealDao = mealDao,
            mealToConsumedProductDao = mealToConsumedProductDao,
            productDao = productDao,
            caloriesPreferences = caloriesPreferences,
        )
    }

}