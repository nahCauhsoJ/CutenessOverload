package com.example.cutenessoverload.di

import android.content.Context
import androidx.room.Room
import com.example.cutenessoverload.database.CuteDAO
import com.example.cutenessoverload.database.CuteDB
import com.example.cutenessoverload.database.LocalRepository
import com.example.cutenessoverload.database.LocalRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    fun cuteDB(@ApplicationContext app: Context): CuteDB =
        Room.databaseBuilder(
            app,
            CuteDB::class.java,
            "saved_cute_db"
        ).build()

    @Provides
    fun cuteDAO(cuteDB: CuteDB): CuteDAO = cuteDB.getCuteDAO()
}