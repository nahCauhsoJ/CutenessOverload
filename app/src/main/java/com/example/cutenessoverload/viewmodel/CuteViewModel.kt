package com.example.cutenessoverload.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cutenessoverload.database.LocalRepositoryInterface
import com.example.cutenessoverload.repository.RepositoryInterfaceFox
import com.example.cutenessoverload.repository.RepositoryInterfaceCat
import com.example.cutenessoverload.repository.RepositoryInterfaceDog
import com.example.cutenessoverload.repository.RepositoryInterfaceDuck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class CuteViewModel @Inject constructor(
    private val repositoryCat: RepositoryInterfaceCat,
    private val repositoryDog: RepositoryInterfaceDog,
    private val repositoryDuck: RepositoryInterfaceDuck,
    private val repositoryFox: RepositoryInterfaceFox,
    private val localRepository: LocalRepositoryInterface,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

}