package com.fullrandom.calories.ui.productinit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullrandom.calories.domain.LoadInitialProductsUseCase
import com.fullrandom.calories.ui.api.CaloriesUiNavKeys
import com.fullrandom.fiti.core.ui.api.navigation.Navigator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ProductInitViewModel.Factory::class)
class ProductInitViewModel @AssistedInject constructor(
    @Assisted private val navKey: CaloriesUiNavKeys.ProductInitScreenNavKey,
    private val loadInitialProductsUseCase: LoadInitialProductsUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    init {
        launchProductInit()
    }

    private fun launchProductInit() {
        viewModelScope.launch {
            loadInitialProductsUseCase()
            navigator.pop()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: CaloriesUiNavKeys.ProductInitScreenNavKey): ProductInitViewModel
    }
}
