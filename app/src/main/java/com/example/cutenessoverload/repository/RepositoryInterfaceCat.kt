package com.example.cutenessoverload.repository

import kotlinx.coroutines.flow.Flow

interface RepositoryInterfaceCat {
    fun getRandomCat(): Flow<RequestState>
}