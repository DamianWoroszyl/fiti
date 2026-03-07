package com.fullrandom.calories.ui.productsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullrandom.calories.domain.SearchProductUseCase
import com.fullrandom.calories.ui.api.CaloriesUiNavKeys
import com.fullrandom.fiti.core.ui.api.navigation.Navigator
import com.fullrandom.model.Product
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@HiltViewModel(assistedFactory = ProductSearchViewModel.Factory::class)
class ProductSearchViewModel @AssistedInject constructor(
    @Assisted private val navKey: CaloriesUiNavKeys.ProductSearchScreenNavKey,
    private val searchProductUseCase: SearchProductUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    private val _uiState = MutableStateFlow(ProductSearchUiState())
    val uiState: StateFlow<ProductSearchUiState> = _uiState

    val screenTitle: String? = computeScreenTitle()

    init {
        observeSearchResults()
    }

    private fun computeScreenTitle(): String? {
        return when (val target = navKey.target) {
            is CaloriesUiNavKeys.ProductSearchScreenNavKey.Target.MealTarget -> {
                val formattedDate = LocalDate.ofEpochDay(target.date)
                    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
                "${target.mealName} · $formattedDate"
            }
            is CaloriesUiNavKeys.ProductSearchScreenNavKey.Target.DishTarget -> target.dishName
            null -> null
        }
    }

    private fun observeSearchResults() {
        viewModelScope.launch {
            _searchQuery
                .flatMapLatest { query: String -> searchProductUseCase(query, limit = 1000) }
                .collect { products: List<Product> ->
                    _uiState.value = _uiState.value.copy(
                        searchQuery = _searchQuery.value,
                        products = products,
                    )
                }
        }
    }

    fun onQueryChanged(query: String) {
        _searchQuery.value = query
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun onProductClicked(product: Product) {
        val addTarget: CaloriesUiNavKeys.ProductDetailsScreenNavKey.AddTarget? =
            when (val target = navKey.target) {
                is CaloriesUiNavKeys.ProductSearchScreenNavKey.Target.MealTarget -> {
                    CaloriesUiNavKeys.ProductDetailsScreenNavKey.AddTarget.MealTarget(
                        mealId = target.mealId,
                        date = target.date,
                    )
                }
                is CaloriesUiNavKeys.ProductSearchScreenNavKey.Target.DishTarget -> {
                    CaloriesUiNavKeys.ProductDetailsScreenNavKey.AddTarget.DishVariantTarget(
                        dishVariantId = target.dishId,
                    )
                }
                null -> null
            }
        navigator.navigate(
            CaloriesUiNavKeys.ProductDetailsScreenNavKey(
                productId = product.id,
                addTarget = addTarget,
            )
        )
    }

    fun onAddProductClicked() {
        navigator.navigate(CaloriesUiNavKeys.ProductEditNavKey())
    }

    fun onBack() {
        navigator.pop()
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: CaloriesUiNavKeys.ProductSearchScreenNavKey): ProductSearchViewModel
    }
}
