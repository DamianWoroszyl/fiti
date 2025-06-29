package com.fullrandom.fiti.calories.storage.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fullrandom.fiti.calories.storage.database.converter.LocalDateConverter
import com.fullrandom.fiti.calories.storage.database.dao.ConsumedProductDao
import com.fullrandom.fiti.calories.storage.database.dao.MealDao
import com.fullrandom.fiti.calories.storage.database.dao.ProductDao
import com.fullrandom.fiti.calories.storage.database.entity.ConsumedProductEntity
import com.fullrandom.fiti.calories.storage.database.entity.MealEntity
import com.fullrandom.fiti.calories.storage.database.entity.ProductEntity

@Database(
    entities = [
        ConsumedProductEntity::class,
        MealEntity::class,
        ProductEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(LocalDateConverter::class)
internal abstract class CaloriesDatabase : RoomDatabase() {

    abstract fun consumedProductDao(): ConsumedProductDao
    abstract fun mealDao(): MealDao
    abstract fun productDao(): ProductDao

    companion object {
        fun getInstance(context: Context): CaloriesDatabase {
            return Room.databaseBuilder(
                    context.applicationContext,
                    CaloriesDatabase::class.java,
                    "fiti_database"
                ).build()
        }
    }
}
