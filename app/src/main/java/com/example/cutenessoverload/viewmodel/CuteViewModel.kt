package com.example.cutenessoverload.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.cutenessoverload.database.LocalRepositoryInterface
import com.example.cutenessoverload.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CuteViewModel @Inject constructor(
    private val repositoryCat: RepositoryInterfaceCat,
    private val repositoryDog: RepositoryInterfaceDog,
    private val repositoryDuck: RepositoryInterfaceDuck,
    private val repositoryFox: RepositoryInterfaceFox,
    private val localRepository: LocalRepositoryInterface,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    // For this pair, first is the previous page number, second is the new one.
    private val _currentPage: MutableLiveData<Pair<Int, Int>> = MutableLiveData(Pair(0,0))
    val currentPage: LiveData<Pair<Int, Int>> get() = _currentPage

    private val _data: MutableLiveData<RequestState> = MutableLiveData()
    val data: LiveData<RequestState> get() = _data

    private val _hasImageResult: MutableLiveData<Boolean> = MutableLiveData()
    val hasImageResult: LiveData<Boolean> get() = _hasImageResult

    fun getRandom() {
        when (Random.nextInt(4)) {
            0 -> getRandomCat()
            1 -> getRandomDog()
            2 -> getRandomDuck()
            3 -> getRandomFox()
        }
    }

    fun getRandomDog() {
        viewModelScope.launch(ioDispatcher) {
            repositoryDog.getRandomDog()
                .collect { _data.postValue(it) }
        }
    }

    fun getRandomFox() {
        viewModelScope.launch(ioDispatcher) {
            repositoryFox.getRandomFox()
                .collect { _data.postValue(it) }
        }
    }

    fun getRandomCat() {
        viewModelScope.launch(ioDispatcher) {
            repositoryCat.getRandomCat()
                .collect { _data.postValue(it) }
        }
    }

    fun getRandomDuck() {
        viewModelScope.launch(ioDispatcher) {
            repositoryDuck.getRandomDuck()
                .collect { _data.postValue(it) }
        }
    }

    // Instead of adding OnPageChangeListener, it's easier to
    //      manage it manually in this case.
    fun changePage(position: Int) {
        val prevPage: Int = currentPage.value?.second ?: 0
        _currentPage.postValue(Pair(prevPage, position))
    }

    fun hasImage(url: String) {
        viewModelScope.launch(ioDispatcher) {
            val result: Int = localRepository.hasImage(url)
            _hasImageResult.postValue(result != 0)
        }
    }
}