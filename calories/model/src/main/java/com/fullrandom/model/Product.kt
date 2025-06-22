package com.fullrandom.model

/**
 * id - id of the Product
 * name - name of the product
 * carbohydrates - carbohydrates / 100g
 * fat = fat / 100g
 * protein = protein / 100g
 * kcal = kcal / 100g
 */
data class Product(
    val id: String,
    val name: String,
    val carbohydrates: Double,
    val fat: Double,
    val protein: Double,
    val kcal: Double,
)
