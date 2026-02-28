package com.fullrandom.model

import java.time.LocalDate

/**
 * @param id - id of that particular consumption
 * @param mealId - id of the meal it was consumed for
 * @param order - order of consumption in meal
 * @param date - date of consumption
 * @param product - consumed product data
 * @param amountGrams - consumed amount in grams
 * @param productName - name of the product, fallback if Product is null
 * @param carbohydrates - carbohydrates / 100g, fallback if Product is null
 * @param fat - fat / 100g, fallback if Product is null
 * @param protein - protein / 100g, fallback if Product is null
 * @param kcal - kcal / 100g, fallback if Product is null
 */
data class ConsumedProduct(
    val id: String,
    val mealId: String,
    val order: Int,
    val date: LocalDate,
    val product: Product?,
    val amountGrams: Double,
    val productName: String, // fallback
    val carbohydrates: Double, // fallback
    val fat: Double, // fallback
    val protein: Double, // fallback
    val kcal: Double, // fallback
)
