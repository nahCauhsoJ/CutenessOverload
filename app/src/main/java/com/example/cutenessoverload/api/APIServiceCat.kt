package com.example.cutenessoverload.api

import com.example.cutenessoverload.model.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServiceCat {
    @GET(RANDOM_PATH)
    suspend fun getRandomCat(
        @Query("json") json: Boolean = true
    ) : Response<Cat>

    @GET(RANDOM_PATH_GIF)
    suspend fun getRandomCatGif(
        @Query("json") json: Boolean = true
    ) : Response<Cat>

    companion object {
        const val BASE_URL = "https://cataas.com"
        private const val BASE_PATH = BASE_URL
        private const val RANDOM_PATH = "${BASE_PATH}/cat"
        private const val RANDOM_PATH_GIF = "${BASE_PATH}/cat/gif"
    }
}