package com.fullrandom.calories.ui.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

object CaloriesUiNavKeys {

    @Serializable
    data object SummaryScreenNavKey : NavKey

    @Serializable
    data object ProductDetailsScreenNavKey : NavKey

}