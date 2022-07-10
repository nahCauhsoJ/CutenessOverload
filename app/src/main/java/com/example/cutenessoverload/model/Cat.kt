package com.example.cutenessoverload.model


import com.google.gson.annotations.SerializedName

data class Cat(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("url")
    val url: String
)