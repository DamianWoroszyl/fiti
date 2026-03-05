package com.fullrandom.calories.domain

import com.fullrandom.model.ConsumedMeal
import com.fullrandom.model.DayCalories
import com.fullrandom.model.Meal
import java.time.LocalDate
import javax.inject.Inject

class SummaryDayCaloriesMapper @Inject constructor() {

    fun map(
        weekDays: List<LocalDate>,
        allMeals: List<Meal>,
        consumedMeals: List<ConsumedMeal>,
    ): List<DayCalories> {
        val consumedByDayAndMealId: Map<Pair<LocalDate, String>, ConsumedMeal> =
            consumedMeals.associateBy { consumedMeal: ConsumedMeal ->
                consumedMeal.date to consumedMeal.meal.id
            }

        return weekDays.map { day: LocalDate ->
            val mealsForDay: List<ConsumedMeal> = allMeals.map { meal: Meal ->
                consumedByDayAndMealId[day to meal.id]
                    ?: ConsumedMeal(meal = meal, date = day, products = emptyList(), dishes = emptyList())
            }
            DayCalories(date = day, consumedMeals = mealsForDay)
        }
    }
}
