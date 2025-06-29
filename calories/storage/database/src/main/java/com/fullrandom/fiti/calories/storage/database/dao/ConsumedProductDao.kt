package com.fullrandom.fiti.calories.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fullrandom.fiti.calories.storage.database.entity.ConsumedProductEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
internal interface ConsumedProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(consumedProducts: List<ConsumedProductEntity>)

    @Query("DELETE FROM consumed_product WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM consumed_product")
    suspend fun clearAll()

    @Query("SELECT * FROM consumed_product WHERE id = :id LIMIT 1")
    fun getById(id: String): Flow<ConsumedProductEntity?>

    @Query("SELECT * FROM consumed_product ORDER BY date DESC")
    fun getAll(): Flow<List<ConsumedProductEntity>>

    @Query("SELECT * FROM consumed_product WHERE date = :date")
    fun getByDate(date: LocalDate): Flow<List<ConsumedProductEntity>>

    @Query("SELECT * FROM consumed_product WHERE meal_id = :mealId")
    fun getByMealId(mealId: String): Flow<List<ConsumedProductEntity>>

    @Query("SELECT * FROM consumed_product WHERE date BETWEEN :startDate AND :endDate")
    fun getByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<ConsumedProductEntity>>
}