package com.example.cutenessoverload.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_cute")
data class CuteEntity(
    @PrimaryKey
    val image_url: String,
    // Types: cat, dog, duck, fox
    val cute_type: String,
    val is_gif: Boolean
)
