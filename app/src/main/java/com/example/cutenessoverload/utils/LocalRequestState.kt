package com.example.cutenessoverload.utils

import com.example.cutenessoverload.model.CuteGeneric
import java.lang.Exception

sealed class LocalRequestState {
    object PENDING: LocalRequestState()
    data class SUCCESS(val response: Pair<String,List<CuteGeneric>>): LocalRequestState()
    data class ERROR(val error: Exception): LocalRequestState()
}
