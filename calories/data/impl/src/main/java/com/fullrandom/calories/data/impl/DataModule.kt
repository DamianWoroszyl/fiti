package com.fullrandom.calories.data.impl

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.fiti.init.AppInitializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideCaloriesRepository(
        caloriesRepository: CaloriesRepositoryImpl
    ): CaloriesRepository = caloriesRepository

    @Provides
    @IntoSet
    fun provideCaloriesInitializer(
        caloriesInitializer: CaloriesInitializer
    ): AppInitializer = caloriesInitializer
}