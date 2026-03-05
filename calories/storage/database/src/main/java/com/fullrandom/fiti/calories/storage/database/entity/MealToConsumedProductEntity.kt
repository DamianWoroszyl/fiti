package com.fullrandom.fiti.calories.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "meal_to_consumed_product",
    primaryKeys = ["meal_id", "product_id"],
    foreignKeys = [
        ForeignKey(
            entity = MealEntity::class,
            parentColumns = ["id"],
            childColumns = ["meal_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ConsumedProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal class MealToConsumedProductEntity(
    @ColumnInfo(name = "meal_id") val mealId: String,
    @ColumnInfo(name = "product_id") val productId: String,
)
