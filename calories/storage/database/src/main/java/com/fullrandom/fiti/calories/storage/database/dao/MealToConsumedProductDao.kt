package com.fullrandom.fiti.calories.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fullrandom.fiti.calories.storage.database.entity.MealToConsumedProductEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
internal interface MealToConsumedProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(joins: List<MealToConsumedProductEntity>)

    @Query("""
        SELECT mcp.* FROM meal_to_consumed_product mcp
        INNER JOIN consumed_product cp ON mcp.product_id = cp.id
        WHERE cp.date BETWEEN :startDate AND :endDate
    """)
    fun observeByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<MealToConsumedProductEntity>>
}
