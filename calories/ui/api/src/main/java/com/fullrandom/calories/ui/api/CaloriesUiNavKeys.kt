package com.fullrandom.calories.ui.api

import com.fullrandom.fiti.core.ui.api.navigation.FitiNavKey
import kotlinx.serialization.Serializable

@Serializable
enum class AddTargetType { MEAL, DISH }

object CaloriesUiNavKeys {

    @Serializable
    data object SummaryScreenNavKey : FitiNavKey

    @Serializable
    data class ProductDetailsScreenNavKey(
        val productId: String,
        val targetId: String? = null,
        val targetType: AddTargetType? = null,
    ) : FitiNavKey

    @Serializable
    data class ProductEditNavKey(
        val productId: String? = null,
    ) : FitiNavKey

}