package com.example.cutenessoverload.repository

import kotlinx.coroutines.flow.Flow

interface RepositoryInterfaceDuck {
    fun getRandomDuck(): Flow<RequestState>
}