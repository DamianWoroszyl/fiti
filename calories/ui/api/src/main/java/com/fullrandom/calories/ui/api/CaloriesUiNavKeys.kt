package com.fullrandom.calories.ui.api

import com.fullrandom.fiti.core.ui.api.navigation.FitiNavKey
import kotlinx.serialization.Serializable

object CaloriesUiNavKeys {

    @Serializable
    data object SummaryScreenNavKey : FitiNavKey

    @Serializable
    data class ProductDetailsScreenNavKey(
        val productId: String,
        val addTarget: AddTarget? = null,
    ) : FitiNavKey {

        @Serializable
        sealed class AddTarget {
            @Serializable
            data class MealTarget(val mealId: String, val date: Long) : AddTarget()

            @Serializable
            data class DishVariantTarget(val dishVariantId: String) : AddTarget()
        }
    }

    @Serializable
    data class ProductEditNavKey(
        val productId: String? = null,
    ) : FitiNavKey

    @Serializable
    data class ProductSearchScreenNavKey(
        val target: Target? = null,
    ) : FitiNavKey {
        @Serializable
        sealed class Target {
            @Serializable
            data class MealTarget(
                val mealId: String,
                val mealName: String,
                val date: Long,
            ) : Target()

            @Serializable
            data class DishTarget(
                val dishId: String,
                val dishName: String,
            ) : Target()
        }
    }

}
