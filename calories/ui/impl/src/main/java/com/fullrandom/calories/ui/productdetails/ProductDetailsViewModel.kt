package com.fullrandom.calories.ui.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullrandom.calories.domain.GetMealUseCase
import com.fullrandom.calories.domain.GetProductUseCase
import com.fullrandom.calories.domain.SaveConsumedCaloriesUseCase
import com.fullrandom.calories.ui.api.CaloriesUiNavKeys
import com.fullrandom.calories.ui.api.CaloriesUiNavKeys.ProductDetailsScreenNavKey.AddTarget
import com.fullrandom.fiti.core.ui.api.navigation.Navigator
import com.fullrandom.model.PreConsumedProduct
import com.fullrandom.model.Product
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

data class ProductDetailsUiState(
    val productName: String = "",
    val kcalPer100g: Double = 0.0,
    val carbsPer100g: Double = 0.0,
    val proteinPer100g: Double = 0.0,
    val fatPer100g: Double = 0.0,
    val targetName: String? = null,
    val gramsInput: String = "100",
    val saveError: String? = null,
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
    private val getProductUseCase: GetProductUseCase,
    private val saveConsumedCaloriesUseCase: SaveConsumedCaloriesUseCase,
    private val getMealUseCase: GetMealUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState

    private var loadedProduct: Product? = null

    init {
        viewModelScope.launch {
            val product: Product? = getProductUseCase(navKey.productId)
            if (product != null) {
                loadedProduct = product
                _uiState.value = _uiState.value.copy(
                    productName = product.name,
                    kcalPer100g = product.kcalPer100g,
                    carbsPer100g = product.carbohydratesPer100g,
                    proteinPer100g = product.proteinPer100g,
                    fatPer100g = product.fatPer100g,
                )
            }
        }

        val addTarget: AddTarget? = navKey.addTarget
        if (addTarget is AddTarget.MealTarget) {
            val mealId: String = addTarget.mealId
            viewModelScope.launch {
                val meal = getMealUseCase(mealId)
                if (meal != null) {
                    _uiState.value = _uiState.value.copy(targetName = meal.name)
                }
            }
        }
    }

    fun onGramsChange(value: String) {
        _uiState.value = _uiState.value.copy(gramsInput = value)
    }

    fun onAddToTarget() {
        viewModelScope.launch {
            val product: Product = loadedProduct ?: return@launch
            val target: AddTarget.MealTarget = navKey.addTarget as? AddTarget.MealTarget ?: return@launch
            val grams: Double = _uiState.value.gramsInput.toDoubleOrNull() ?: return@launch
            val date: LocalDate = LocalDate.ofEpochDay(target.date)
            val preConsumedProduct = PreConsumedProduct(
                product = product,
                amountGrams = grams,
                mealId = target.mealId,
                date = date,
                order = 0,
            )
            val result: Result<Unit> = saveConsumedCaloriesUseCase(target.mealId, listOf(preConsumedProduct))
            result.fold(
                onSuccess = { navigator.pop() },
                onFailure = { error: Throwable ->
                    _uiState.value = _uiState.value.copy(saveError = error.message)
                },
            )
        }
    }

    fun onSaveErrorDismissed() {
        _uiState.value = _uiState.value.copy(saveError = null)
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
