package com.example.cutenessoverload.repository

import com.example.cutenessoverload.api.APIServiceCat
import com.example.cutenessoverload.utils.RequestState
import com.example.cutenessoverload.utils.toCuteGeneric
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class RepositoryCat @Inject constructor(
    private val apiServiceCat: APIServiceCat
) : RepositoryInterfaceCat {
    override fun getRandomCat(): Flow<RequestState> =
        flow {
            emit(RequestState.PENDING)
            try {
                val response =
                    if (Random.nextFloat() > 0.8)
                        apiServiceCat.getRandomCatGif()
                    else
                        apiServiceCat.getRandomCat()
                if (response.isSuccessful)
                    response.body()?.let {
                        emit(RequestState.SUCCESS(it.toCuteGeneric()))
                    } ?: throw Exception("No body")
                else throw Exception(response.errorBody()?.toString())
            } catch (e: Exception) { RequestState.ERROR(e) }
        }
}