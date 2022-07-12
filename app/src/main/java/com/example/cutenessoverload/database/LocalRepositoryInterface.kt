package com.example.cutenessoverload.database

import com.example.cutenessoverload.utils.LocalRequestState
import kotlinx.coroutines.flow.Flow

interface LocalRepositoryInterface {
    suspend fun getAllCute(requesterId: String): Flow<LocalRequestState>
    suspend fun getCuteByType(requesterId: String, cute_type: String): Flow<LocalRequestState>
    suspend fun saveCute(cute: CuteEntity): Flow<Boolean>
    suspend fun unsaveCute(cute: CuteEntity): Flow<Boolean>
    suspend fun unsaveCute(cute: List<CuteEntity>): Flow<Boolean>
    suspend fun hasImage(url: String): Int
}