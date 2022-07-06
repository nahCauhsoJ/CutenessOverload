package com.example.cutenessoverload.di

import com.example.cutenessoverload.api.APIServiceCat
import com.example.cutenessoverload.api.APIServiceDog
import com.example.cutenessoverload.api.APIServiceDuck
import com.example.cutenessoverload.api.APIServiceFox
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun gson(): Gson = Gson()
    
    @Provides
    fun gsonFactory(gson: Gson): GsonConverterFactory = 
        GsonConverterFactory.create(gson)

    @Provides
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    
    @Provides
    fun apiServiceDog(
        okHttpClient: OkHttpClient, 
        gsonFactory: GsonConverterFactory): APIServiceDog =
        Retrofit.Builder()
            .baseUrl(APIServiceDog.BASE_URL)
            .addConverterFactory(gsonFactory)
            .client(okHttpClient)
            .build()
            .create(APIServiceDog::class.java)

    @Provides
    fun apiServiceCat(
        okHttpClient: OkHttpClient,
        gsonFactory: GsonConverterFactory): APIServiceCat =
        Retrofit.Builder()
            .baseUrl(APIServiceCat.BASE_URL)
            .addConverterFactory(gsonFactory)
            .client(okHttpClient)
            .build()
            .create(APIServiceCat::class.java)

    @Provides
    fun apiServiceFox(
        okHttpClient: OkHttpClient,
        gsonFactory: GsonConverterFactory): APIServiceFox =
        Retrofit.Builder()
            .baseUrl(APIServiceFox.BASE_URL)
            .addConverterFactory(gsonFactory)
            .client(okHttpClient)
            .build()
            .create(APIServiceFox::class.java)

    @Provides
    fun apiServiceDuck(
        okHttpClient: OkHttpClient,
        gsonFactory: GsonConverterFactory): APIServiceDuck =
        Retrofit.Builder()
            .baseUrl(APIServiceDuck.BASE_URL)
            .addConverterFactory(gsonFactory)
            .client(okHttpClient)
            .build()
            .create(APIServiceDuck::class.java)
}