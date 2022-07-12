package com.example.cutenessoverload.database

import com.example.cutenessoverload.utils.LocalRequestState
import com.example.cutenessoverload.utils.toCuteGeneric
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val cuteDAO: CuteDAO
) : LocalRepositoryInterface {
    override suspend fun getAllCute(requesterId: String): Flow<LocalRequestState> =
        flow {
            emit(LocalRequestState.PENDING)
            try {
                val response = cuteDAO.getAllCute()
                emit(LocalRequestState.SUCCESS(Pair(requesterId,response.map{ it.toCuteGeneric() })))
            } catch (e: Exception) { LocalRequestState.ERROR(e) }
        }

    override suspend fun getCuteByType(requesterId: String, cute_type: String): Flow<LocalRequestState> =
        flow {
            emit(LocalRequestState.PENDING)
            try {
                val response = cuteDAO.getCuteByType(cute_type)
                emit(LocalRequestState.SUCCESS(Pair(requesterId, response.map{ it.toCuteGeneric() })))
            } catch (e: Exception) { LocalRequestState.ERROR(e) }
        }


    override suspend fun saveCute(cute: CuteEntity): Flow<Boolean> =
        flow {
            try {
                cuteDAO.saveCute(cute)
                emit(true)
            } catch (e: Exception) { emit(false) }
        }


    override suspend fun unsaveCute(cute: CuteEntity): Flow<Boolean> =
        flow {
            try {
                cuteDAO.unsaveCute(cute)
                emit(true)
            } catch (e: Exception) { emit(false) }
        }
    override suspend fun unsaveCute(cute: List<CuteEntity>): Flow<Boolean> =
        flow {
            try {
                cuteDAO.unsaveCute(cute)
                emit(true)
            } catch (e: Exception) { emit(false) }
        }

    override suspend fun hasImage(url: String): Int =
        cuteDAO.hasImage(url)
}