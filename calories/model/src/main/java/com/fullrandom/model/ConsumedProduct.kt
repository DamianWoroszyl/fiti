package com.fullrandom.model

import java.time.LocalDate

/**
 * @param id - id of the Product
 * @param productName - name of the product
 * @param carbohydrates - carbohydrates / 100g, fallback if Product is null
 * @param fat - fat / 100g, fallback if Product is null
 * @param protein - protein / 100g, fallback if Product is null
 * @param kcal - kcal / 100g, fallback if Product is null
 * @param amountGrams - consumed amount in grams
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
