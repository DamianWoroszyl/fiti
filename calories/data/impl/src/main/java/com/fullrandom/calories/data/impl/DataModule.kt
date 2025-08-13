package com.fullrandom.calories.data.impl

import com.fullrandom.calories.data.api.CaloriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideCaloriesRepository(
        caloriesRepository: CaloriesRepositoryImpl
    ): CaloriesRepository = caloriesRepository

}