package com.example.cutenessoverload.database

interface LocalRepositoryInterface {
    suspend fun getAllCute(): List<CuteEntity>
    suspend fun getCuteByType(cute_type: String): List<CuteEntity>
    suspend fun saveCute(cute: CuteEntity)
    suspend fun unsaveCute(cute: CuteEntity)
    suspend fun hasImage(url: String): Int
}