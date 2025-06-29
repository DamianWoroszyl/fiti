package com.fullrandom.fiti.calories.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fullrandom.fiti.calories.storage.database.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: MealEntity)

    @Update
    suspend fun update(meal: MealEntity)

    @Query("DELETE FROM meal WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM meal WHERE id = :id LIMIT 1")
    fun getById(id: String): Flow<MealEntity?>

    @Query("SELECT * FROM meal ORDER BY `order` ASC")
    fun observeAll(): Flow<List<MealEntity>>

    @Query("DELETE FROM meal")
    suspend fun clearAll()
}