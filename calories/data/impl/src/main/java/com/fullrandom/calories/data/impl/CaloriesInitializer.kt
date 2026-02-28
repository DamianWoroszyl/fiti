package com.fullrandom.calories.data.impl

import com.fullrandom.fiti.init.AppInitializer
import com.fullrandom.fiti.storage.api.CaloriesStorage
import com.fullrandom.model.Meal
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

class CaloriesInitializer @Inject constructor(
    private val caloriesStorage: CaloriesStorage,
): AppInitializer {

    override suspend fun doInitWork() {
        val hasMeals = caloriesStorage.observeMeals().first().isNotEmpty()
        if (hasMeals) return

        val defaultMeals = listOf("Breakfast", "Lunch", "Afternoon Snack", "Dinner")
        defaultMeals.forEachIndexed { index, name ->
            caloriesStorage.saveMeal(
                Meal(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    order = index,
                )
            )
        }
    }
}