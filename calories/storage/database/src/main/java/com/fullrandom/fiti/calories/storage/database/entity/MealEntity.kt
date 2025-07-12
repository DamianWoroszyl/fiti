package com.fullrandom.fiti.calories.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fullrandom.model.Meal

@Entity(tableName = "meal")
internal class MealEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "order") val order: Int,
) {
    companion object {
        fun fromDomain(meal: Meal): MealEntity {
            return MealEntity(
                id = meal.id,
                name = meal.name,
                order = meal.order
            )
        }
    }
}

internal fun MealEntity.toDomain(): Meal {
    return Meal(
        id = this.id,
        name = this.name,
        order = this.order
    )
}