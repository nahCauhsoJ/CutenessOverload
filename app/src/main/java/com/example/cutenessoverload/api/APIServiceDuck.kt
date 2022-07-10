package com.example.cutenessoverload.api

import com.example.cutenessoverload.model.Duck
import retrofit2.Response
import retrofit2.http.GET

interface APIServiceDuck {
    @GET(RANDOM_PATH)
    suspend fun getRandomDuck() : Response<Duck>

    companion object {
        const val BASE_URL = "https://random-d.uk/"
        private const val BASE_PATH = "${BASE_URL}api/v2"
        private const val RANDOM_PATH = "${BASE_PATH}/random"
    }
}