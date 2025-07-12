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
                carbohydrates = product.carbohydrates,
                fat = product.fat,
                protein = product.protein,
                kcal = product.kcal
            )
        }
    }
}

internal fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        carbohydrates = this.carbohydrates,
        fat = this.fat,
        protein = this.protein,
        kcal = this.kcal
    )
}