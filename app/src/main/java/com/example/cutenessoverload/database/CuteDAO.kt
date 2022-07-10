package com.example.cutenessoverload.database

import androidx.room.*

@Dao
interface CuteDAO {
    @Query("SELECT * FROM saved_cute")
    suspend fun getAllCute(): List<CuteEntity>

    @Query("SELECT * FROM saved_cute WHERE cute_type == :cute_type")
    suspend fun getCuteByType(cute_type: String): List<CuteEntity>

    @Query("SELECT COUNT(1) FROM saved_cute WHERE image_url == :image_url")
    suspend fun hasImage(image_url: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCute(cute: CuteEntity)

    @Delete
    suspend fun unsaveCute(cute: CuteEntity)
}