package com.fullrandom.calories.data.impl

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.calories.data.api.ProductLoader
import com.fullrandom.fiti.init.AppInitializer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Binds
    fun bindProductLoader(impl: ProductLoaderImpl): ProductLoader

    companion object {

        @Singleton
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
}