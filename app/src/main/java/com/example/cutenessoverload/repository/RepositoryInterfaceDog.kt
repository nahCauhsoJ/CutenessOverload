package com.example.cutenessoverload.repository

import kotlinx.coroutines.flow.Flow

interface RepositoryInterfaceDog {
    fun getRandomDog(): Flow<RequestState>
}