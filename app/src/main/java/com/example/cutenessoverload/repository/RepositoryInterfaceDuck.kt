package com.example.cutenessoverload.repository

import com.example.cutenessoverload.utils.RequestState
import kotlinx.coroutines.flow.Flow

interface RepositoryInterfaceDuck {
    fun getRandomDuck(): Flow<RequestState>
}