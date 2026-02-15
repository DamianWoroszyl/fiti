package com.fullrandom.calories.ui.productedit

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = ProductEditViewModel.Factory::class)
class ProductEditViewModel @AssistedInject constructor(
    @Assisted private val productId: String,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(productId: String): ProductEditViewModel
    }
}