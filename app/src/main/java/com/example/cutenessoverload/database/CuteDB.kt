package com.example.cutenessoverload.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CuteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CuteDB: RoomDatabase() {
    abstract fun getCuteDAO(): CuteDAO
}