package com.example.cutenessoverload.model


import com.google.gson.annotations.SerializedName

data class Duck(
    @SerializedName("message")
    val message: String,
    @SerializedName("url")
    val url: String
)