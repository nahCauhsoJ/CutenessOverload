package com.example.cutenessoverload.repository

import android.util.Log
import com.example.cutenessoverload.api.APIServiceFox
import com.example.cutenessoverload.utils.toCuteGeneric
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryFox @Inject constructor(
    private val apiServiceFox: APIServiceFox
) : RepositoryInterfaceFox {
    override fun getRandomFox(): Flow<RequestState> =
        flow {
            emit(RequestState.PENDING)
            try {
                val response = apiServiceFox.getRandomFox()
                if (response.isSuccessful)
                    response.body()?.let {
                        emit(RequestState.SUCCESS(it.toCuteGeneric()))
                    } ?: throw Exception("No body")
                else throw Exception(response.errorBody()?.toString())
            } catch (e: Exception) { RequestState.ERROR(e) }
        }
}