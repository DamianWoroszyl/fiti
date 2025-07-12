package com.fullrandom.fiti.calories.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fullrandom.fiti.calories.storage.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity)

    @Query("DELETE FROM product WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM product WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ProductEntity?

    @Query("SELECT * FROM product WHERE id = :id LIMIT 1")
    fun observeById(id: String): Flow<ProductEntity?>

    @Query("SELECT * FROM product WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchByName(query: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<ProductEntity>>
}