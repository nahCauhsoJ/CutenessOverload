package com.example.cutenessoverload.model

data class CuteGeneric(
    // The URL is the ID.
    val image_url: String,
    // Types: cat, dog, duck, fox
    val cute_type: String,
    val is_gif: Boolean
)
