package com.fullrandom.calories.ui.di

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fullrandom.calories.ui.api.CaloriesUiNavKeys
import com.fullrandom.calories.ui.productdetails.ProductDetailsScreen
import com.fullrandom.calories.ui.productdetails.ProductDetailsViewModel
import com.fullrandom.calories.ui.productedit.ProductEditScreen
import com.fullrandom.calories.ui.productedit.ProductEditViewModel
import com.fullrandom.calories.ui.summary.CaloriesSummaryScreen
import com.fullrandom.calories.ui.summary.CaloriesSummaryViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@InstallIn(ActivityRetainedComponent::class)
@Module
object NavModule {

    @IntoSet
    @Provides
    fun featureScreensEntryBuilder(): EntryProviderScope<NavKey>.() -> Unit = {
        entry<CaloriesUiNavKeys.SummaryScreenNavKey> { key ->
            CaloriesSummaryScreen(
                viewModel = hiltViewModel<CaloriesSummaryViewModel, CaloriesSummaryViewModel.Factory>(
                    creationCallback = { factory ->
                        factory.create(key)
                    }
                )
            )
        }
        entry<CaloriesUiNavKeys.ProductDetailsScreenNavKey> { key ->
            ProductDetailsScreen(
                viewModel = hiltViewModel<ProductDetailsViewModel, ProductDetailsViewModel.Factory>(
                    creationCallback = { factory ->
                        factory.create(key)
                    }
                )
            )
        }
        entry<CaloriesUiNavKeys.ProductEditNavKey> { key ->
            ProductEditScreen(
                viewModel = hiltViewModel<ProductEditViewModel, ProductEditViewModel.Factory>(
                    creationCallback = { factory ->
                        factory.create(key)
                    }
                )
            )
        }
    }
}
