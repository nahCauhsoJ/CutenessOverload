package com.example.cutenessoverload.di

import com.example.cutenessoverload.database.LocalRepository
import com.example.cutenessoverload.database.LocalRepositoryInterface
import com.example.cutenessoverload.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds abstract fun repositoryDog(repositoryDog: RepositoryDog): RepositoryInterfaceDog
    @Binds abstract fun repositoryCat(repositoryCat: RepositoryCat): RepositoryInterfaceCat
    @Binds abstract fun repositoryFox(repositoryFox: RepositoryFox): RepositoryInterfaceFox
    @Binds abstract fun repositoryDuck(repositoryDuck: RepositoryDuck): RepositoryInterfaceDuck

    @Binds abstract fun localRepository(localRepository: LocalRepository) : LocalRepositoryInterface
}