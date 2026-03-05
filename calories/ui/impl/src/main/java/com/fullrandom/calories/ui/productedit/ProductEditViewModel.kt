package com.fullrandom.calories.ui.productedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullrandom.calories.domain.DeleteProductUseCase
import com.fullrandom.calories.domain.GetProductUseCase
import com.fullrandom.calories.domain.SaveProductUseCase
import com.fullrandom.calories.ui.api.CaloriesUiNavKeys
import com.fullrandom.fiti.core.ui.api.navigation.Navigator
import com.fullrandom.model.Product
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ProductEditViewModel.Factory::class)
class ProductEditViewModel @AssistedInject constructor(
    @Assisted private val navKey: CaloriesUiNavKeys.ProductEditNavKey,
    private val navigator: Navigator,
    private val getProductUseCase: GetProductUseCase,
    private val saveProductUseCase: SaveProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductEditUiState(isEditMode = navKey.productId != null))
    val uiState: StateFlow<ProductEditUiState> = _uiState

    init {
        val productId = navKey.productId
        if (productId != null) {
            viewModelScope.launch {
                val product = getProductUseCase(productId)
                if (product != null) {
                    _uiState.value = _uiState.value.copy(
                        productName = product.name,
                        kcalPer100g = product.kcalPer100g.toString(),
                        carbsPer100g = product.carbohydratesPer100g.toString(),
                        proteinPer100g = product.proteinPer100g.toString(),
                        fatPer100g = product.fatPer100g.toString(),
                    )
                }
            }
        }
    }

    fun onNameChange(value: String) {
        _uiState.value = _uiState.value.copy(productName = value)
    }

    fun onKcalChange(value: String) {
        _uiState.value = _uiState.value.copy(kcalPer100g = value)
    }

    fun onCarbsChange(value: String) {
        _uiState.value = _uiState.value.copy(carbsPer100g = value)
    }

    fun onProteinChange(value: String) {
        _uiState.value = _uiState.value.copy(proteinPer100g = value)
    }

    fun onFatChange(value: String) {
        _uiState.value = _uiState.value.copy(fatPer100g = value)
    }

    fun onSave() {
        viewModelScope.launch {
            val state = _uiState.value
            val product = Product(
                id = navKey.productId ?: "",
                name = state.productName,
                kcalPer100g = state.kcalPer100g.toDoubleOrNull() ?: 0.0,
                carbohydratesPer100g = state.carbsPer100g.toDoubleOrNull() ?: 0.0,
                proteinPer100g = state.proteinPer100g.toDoubleOrNull() ?: 0.0,
                fatPer100g = state.fatPer100g.toDoubleOrNull() ?: 0.0,
            )
            saveProductUseCase(product)
            navigator.pop()
        }
    }

    fun onDelete() {
        val productId = navKey.productId ?: return
        viewModelScope.launch {
            deleteProductUseCase(productId)
            navigator.pop()
        }
    }

    fun onBack() {
        navigator.pop()
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: CaloriesUiNavKeys.ProductEditNavKey): ProductEditViewModel
    }
}
