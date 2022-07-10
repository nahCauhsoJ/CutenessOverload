package com.example.cutenessoverload.repository

import kotlinx.coroutines.flow.Flow

interface RepositoryInterfaceFox {
    fun getRandomFox(): Flow<RequestState>
}