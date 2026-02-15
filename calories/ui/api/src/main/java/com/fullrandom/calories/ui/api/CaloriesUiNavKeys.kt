package com.fullrandom.calories.ui.api

import com.fullrandom.fiti.core.ui.api.navigation.FitiNavKey
import kotlinx.serialization.Serializable

object CaloriesUiNavKeys {

    @Serializable
    data object SummaryScreenNavKey : FitiNavKey

    @Serializable
    data class ProductDetailsScreenNavKey(
        val productId: String,
    ) : FitiNavKey

}