package com.fullrandom.calories.ui.productedit

data class ProductEditUiState(
    val productName: String = "",
    val kcalPer100g: String = "",
    val carbsPer100g: String = "",
    val proteinPer100g: String = "",
    val fatPer100g: String = "",
    val isEditMode: Boolean = false,
)
