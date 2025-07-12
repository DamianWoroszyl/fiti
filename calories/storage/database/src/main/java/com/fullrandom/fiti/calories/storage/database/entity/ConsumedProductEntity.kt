package com.fullrandom.fiti.calories.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fullrandom.model.ConsumedProduct
import java.time.LocalDate

@Entity(tableName = "consumed_product")
internal class ConsumedProductEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "meal_id") val mealId: String,
    @ColumnInfo(name = "product_id") val productId: String?,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "amount_grams") val amountGrams: Double,
    @ColumnInfo(name = "product_name") val productName: String,
    @ColumnInfo(name = "carbohydrates") val carbohydrates: Double, // fallback
    @ColumnInfo(name = "fat") val fat: Double, // fallback
    @ColumnInfo(name = "protein") val protein: Double, // fallback
    @ColumnInfo(name = "kcal") val kcal: Double, // fallback
) {
    companion object {
        fun fromDomain(
            consumedProduct: ConsumedProduct
        ): ConsumedProductEntity {
            return ConsumedProductEntity(
                id = consumedProduct.id,
                mealId = consumedProduct.mealId,
                productId = consumedProduct.product?.id,
                order = consumedProduct.order,
                date = consumedProduct.date,
                amountGrams = consumedProduct.amountGrams,
                productName = consumedProduct.product?.name ?: consumedProduct.productName,
                carbohydrates = consumedProduct.product?.carbohydrates ?: consumedProduct.carbohydrates,
                fat = consumedProduct.product?.fat ?: consumedProduct.fat,
                protein = consumedProduct.product?.protein ?: consumedProduct.protein,
                kcal = consumedProduct.product?.kcal ?: consumedProduct.kcal
            )
        }
    }
}