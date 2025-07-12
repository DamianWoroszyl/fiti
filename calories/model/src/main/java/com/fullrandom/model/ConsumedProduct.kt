package com.fullrandom.model

import java.time.LocalDate

/**
 * id - id of the Product
 * name - name of the product
 * carbohydrates - carbohydrates / 100g, fallback if Product is null
 * fat - fat / 100g, fallback if Product is null
 * protein - protein / 100g, fallback if Product is null
 * kcal - kcal / 100g, fallback if Product is null
 * amountGrams - consumed amount in grams
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
