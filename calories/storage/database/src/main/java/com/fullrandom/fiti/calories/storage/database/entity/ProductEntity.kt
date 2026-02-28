package com.fullrandom.fiti.calories.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fullrandom.model.Product

@Entity(tableName = "product")
internal class ProductEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "carbohydrates") val carbohydrates: Double,
    @ColumnInfo(name = "fat") val fat: Double,
    @ColumnInfo(name = "protein") val protein: Double,
    @ColumnInfo(name = "kcal") val kcal: Double,
) {
    companion object {
        fun fromDomain(product: Product): ProductEntity {
            return ProductEntity(
                id = product.id,
                name = product.name,
                carbohydrates = product.carbohydratesPer100g,
                fat = product.fatPer100g,
                protein = product.proteinPer100g,
                kcal = product.kcalPer100g
            )
        }
    }
}

internal fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        carbohydratesPer100g = this.carbohydrates,
        fatPer100g = this.fat,
        proteinPer100g = this.protein,
        kcalPer100g = this.kcal
    )
}