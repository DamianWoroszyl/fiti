package com.fullrandom.fiti.calories.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fullrandom.model.ConsumedProduct
import java.time.LocalDate

@Entity(tableName = "consumed_product")
internal class ConsumedProductEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "product_id") val productId: String?,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "amount_grams") val amountGrams: Double,
    @ColumnInfo(name = "product_name") val productName: String,
    @ColumnInfo(name = "carbohydrates") val carbohydratesPer100g: Double,
    @ColumnInfo(name = "fat") val fatPer100g: Double,
    @ColumnInfo(name = "protein") val proteinPer100g: Double,
    @ColumnInfo(name = "kcal") val kcalPer100g: Double,
) {
    companion object {
        fun fromDomain(
            consumedProduct: ConsumedProduct
        ): ConsumedProductEntity {
            return ConsumedProductEntity(
                id = consumedProduct.id,
                productId = consumedProduct.product?.id,
                order = consumedProduct.order,
                date = consumedProduct.date,
                amountGrams = consumedProduct.amountGrams,
                productName = consumedProduct.product?.name ?: consumedProduct.productName,
                carbohydratesPer100g = consumedProduct.product?.carbohydratesPer100g ?: consumedProduct.carbohydratesPer100g,
                fatPer100g = consumedProduct.product?.fatPer100g ?: consumedProduct.fatPer100g,
                proteinPer100g = consumedProduct.product?.proteinPer100g ?: consumedProduct.proteinPer100g,
                kcalPer100g = consumedProduct.product?.kcalPer100g ?: consumedProduct.kcalPer100g
            )
        }
    }
}