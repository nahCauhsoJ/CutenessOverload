package com.example.cutenessoverload.repository

import com.example.cutenessoverload.model.CuteGeneric
import java.lang.Exception

sealed class RequestState {
    object PENDING: RequestState()
    data class SUCCESS(val response: CuteGeneric): RequestState()
    data class ERROR(val error: Exception): RequestState()
}
