package com.fullrandom.model

import java.time.LocalDate

/**
 * @param id - id of that particular consumption
 * @param order - order of consumption in meal or dish
 * @param date - date of consumption
 * @param product - consumed product data
 * @param amountGrams - consumed amount in grams
 * @param productName - name of the product, fallback if Product is null
 * @param carbohydratesPer100g - carbohydrates / 100g, fallback if Product is null
 * @param fatPer100g - fat / 100g, fallback if Product is null
 * @param proteinPer100g - protein / 100g, fallback if Product is null
 * @param kcalPer100g - kcal / 100g, fallback if Product is null
 */
data class ConsumedProduct(
    val id: String,
    val order: Int,
    val date: LocalDate,
    val product: Product?,
    val amountGrams: Double,
    val productName: String, // fallback
    val carbohydratesPer100g: Double, // fallback
    val fatPer100g: Double, // fallback
    val proteinPer100g: Double, // fallback
    val kcalPer100g: Double, // fallback
)
