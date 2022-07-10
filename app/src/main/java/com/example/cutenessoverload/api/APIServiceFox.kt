package com.example.cutenessoverload.api

import android.text.Html
import com.example.cutenessoverload.model.Dog
import com.example.cutenessoverload.model.Fox
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface APIServiceFox {
    @GET(RANDOM_URL)
    suspend fun getRandomFox() : Response<Fox>

    companion object {
        const val BASE_URL = "https://randomfox.ca/"
        private const val RANDOM_URL = "${BASE_URL}floof"
    }
}