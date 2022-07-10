package com.example.cutenessoverload.model


import com.google.gson.annotations.SerializedName

data class Fox(
    @SerializedName("image")
    val image: String,
    @SerializedName("link")
    val link: String
)