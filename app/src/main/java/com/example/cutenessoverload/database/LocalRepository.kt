package com.example.cutenessoverload.database

import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val cuteDAO: CuteDAO
) : LocalRepositoryInterface {
    override suspend fun getAllCute(): List<CuteEntity> =
        cuteDAO.getAllCute()

    override suspend fun getCuteByType(cute_type: String): List<CuteEntity> =
        cuteDAO.getCuteByType(cute_type)

    override suspend fun saveCute(cute: CuteEntity) =
        cuteDAO.saveCute(cute)

    override suspend fun unsaveCute(cute: CuteEntity) =
        cuteDAO.unsaveCute(cute)

    override suspend fun hasImage(url: String): Int =
        cuteDAO.hasImage(url)
}