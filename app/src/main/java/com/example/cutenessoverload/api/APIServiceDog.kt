package com.example.cutenessoverload.api

import com.example.cutenessoverload.model.Dog
import retrofit2.Response
import retrofit2.http.GET

interface APIServiceDog {
    @GET(RANDOM_PATH)
    suspend fun getRandomDog() : Response<Dog>

    companion object {
        const val BASE_URL = "https://dog.ceo/"
        private const val BASE_PATH = "${BASE_URL}api/breeds"
        private const val RANDOM_PATH = "${BASE_PATH}/image/random"
    }
}