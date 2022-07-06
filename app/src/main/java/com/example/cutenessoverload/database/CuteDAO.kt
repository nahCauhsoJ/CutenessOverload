package com.example.cutenessoverload.database

import androidx.room.*

@Dao
interface CuteDAO {
    @Query("SELECT * FROM saved_cute")
    suspend fun getAllCute(): List<CuteEntity>

    @Query("SELECT * FROM saved_cute WHERE cute_type == :cute_type")
    suspend fun getCuteByType(cute_type: String): List<CuteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCute(cute: CuteEntity)

    @Delete
    suspend fun unsaveCute(cute: CuteEntity)
}