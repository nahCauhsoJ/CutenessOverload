package com.example.cutenessoverload.repository

import com.example.cutenessoverload.api.APIServiceDog
import com.example.cutenessoverload.utils.toCuteGeneric
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryDog @Inject constructor(
    private val apiServiceDog: APIServiceDog
) : RepositoryInterfaceDog {
    override fun getRandomDog(): Flow<RequestState> =
        flow {
            emit(RequestState.PENDING)
            try {
                val response = apiServiceDog.getRandomDog()
                if (response.isSuccessful)
                    response.body()?.let {
                        emit(RequestState.SUCCESS(it.toCuteGeneric()))
                    } ?: throw Exception("No body")
                else throw Exception(response.errorBody()?.toString())
            } catch (e: Exception) { RequestState.ERROR(e) }
        }
}