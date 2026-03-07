package com.fullrandom.calories.ui.productsearch

import com.fullrandom.model.Product

data class ProductSearchUiState(
    val searchQuery: String = "",
    val products: List<Product> = emptyList(),
)
