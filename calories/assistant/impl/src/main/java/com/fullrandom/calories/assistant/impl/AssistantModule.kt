package com.fullrandom.calories.assistant.impl

import com.fullrandom.calories.assistant.api.CaloriesAssistant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AssistantModule {

    @Provides
    fun provideCaloriesAssistant(
        caloriesAssistant: CaloriesAssistantImpl
    ): CaloriesAssistant = caloriesAssistant

}