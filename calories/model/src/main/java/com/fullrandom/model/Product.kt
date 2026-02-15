package com.fullrandom.model

/**
 * @param id - id of the Product
 * @param name - name of the product
 * @param carbohydrates - carbohydrates / 100g
 * @param fat = fat / 100g
 * @param protein = protein / 100g
 * @param kcal = kcal / 100g
 */
data class Product(
    val id: String,
    val name: String,
    val carbohydrates: Double,
    val fat: Double,
    val protein: Double,
    val kcal: Double,
)
