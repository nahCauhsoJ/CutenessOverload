package com.example.cutenessoverload.repository

import com.example.cutenessoverload.api.APIServiceDuck
import com.example.cutenessoverload.utils.RequestState
import com.example.cutenessoverload.utils.toCuteGeneric
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryDuck @Inject constructor(
    private val apiServiceDuck: APIServiceDuck
) : RepositoryInterfaceDuck {
    override fun getRandomDuck(): Flow<RequestState> =
        flow {
            emit(RequestState.PENDING)
            try {
                val response = apiServiceDuck.getRandomDuck()
                if (response.isSuccessful)
                    response.body()?.let {
                        emit(RequestState.SUCCESS(it.toCuteGeneric()))
                    } ?: throw Exception("No body")
                else throw Exception(response.errorBody()?.toString())
            } catch (e: Exception) { RequestState.ERROR(e) }
        }
}