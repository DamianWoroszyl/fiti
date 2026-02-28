package com.fullrandom.model

/**
 * @param id - id of the Product
 * @param name - name of the product
 * @param carbohydratesPer100g - carbohydrates / 100g
 * @param fatPer100g = fat / 100g
 * @param proteinPer100g = protein / 100g
 * @param kcalPer100g = kcal / 100g
 */
data class Product(
    val id: String,
    val name: String,
    val carbohydratesPer100g: Double,
    val fatPer100g: Double,
    val proteinPer100g: Double,
    val kcalPer100g: Double,
)
