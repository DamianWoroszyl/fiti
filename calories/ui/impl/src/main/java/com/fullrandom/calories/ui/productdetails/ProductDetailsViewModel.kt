package com.fullrandom.calories.ui.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullrandom.calories.ui.api.CaloriesUiNavKeys
import com.fullrandom.fiti.core.ui.api.navigation.Navigator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProductDetailsUiState(
    val productName: String = "",
    val kcalPer100g: Double = 0.0,
    val carbsPer100g: Double = 0.0,
    val proteinPer100g: Double = 0.0,
    val fatPer100g: Double = 0.0,
    val targetName: String? = null,
    val gramsInput: String = "100",
) {
    private val grams: Double get() = gramsInput.toDoubleOrNull() ?: 0.0
    val kcalForGrams: Double get() = kcalPer100g * grams / 100
    val carbsForGrams: Double get() = carbsPer100g * grams / 100
    val proteinForGrams: Double get() = proteinPer100g * grams / 100
    val fatForGrams: Double get() = fatPer100g * grams / 100
}

@HiltViewModel(assistedFactory = ProductDetailsViewModel.Factory::class)
class ProductDetailsViewModel @AssistedInject constructor(
    @Assisted private val navKey: CaloriesUiNavKeys.ProductDetailsScreenNavKey,
    private val navigator: Navigator,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState

    init {
        viewModelScope.launch {
            // TODO: load product by navKey.productId using GetProductUseCase
        }
        if (navKey.targetId != null) {
            viewModelScope.launch {
                // TODO: load target name by navKey.targetId / navKey.targetType using appropriate use case
            }
        }
    }

    fun onGramsChange(value: String) {
        _uiState.value = _uiState.value.copy(gramsInput = value)
    }

    fun onAddToTarget() {
        viewModelScope.launch {
            // TODO: call SaveConsumedCaloriesUseCase with product, grams, and navKey.targetId
            navigator.pop()
        }
    }

    fun onEditProduct() {
        navigator.navigate(CaloriesUiNavKeys.ProductEditNavKey(navKey.productId))
    }

    fun onBack() {
        navigator.pop()
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: CaloriesUiNavKeys.ProductDetailsScreenNavKey): ProductDetailsViewModel
    }
}
