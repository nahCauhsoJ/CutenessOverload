package com.example.cutenessoverload.repository

import com.example.cutenessoverload.utils.RequestState
import kotlinx.coroutines.flow.Flow

interface RepositoryInterfaceFox {
    fun getRandomFox(): Flow<RequestState>
}