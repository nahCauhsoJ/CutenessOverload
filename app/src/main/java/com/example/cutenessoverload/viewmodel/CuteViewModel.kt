package com.example.cutenessoverload.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cutenessoverload.database.LocalRepositoryInterface
import com.example.cutenessoverload.model.CuteGeneric
import com.example.cutenessoverload.repository.*
import com.example.cutenessoverload.utils.LocalRequestState
import com.example.cutenessoverload.utils.RequestState
import com.example.cutenessoverload.utils.toCuteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.exp
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
    private var getPicJob: Job? = null

    private val _hasImageResult: MutableLiveData<Boolean> = MutableLiveData()
    val hasImageResult: LiveData<Boolean> get() = _hasImageResult
    private var hasImageJob: Job? = null

    // true means it's saving and can't take another order. false otherwise.
    private val _savingImage: MutableLiveData<Boolean> = MutableLiveData()
    val savingImage: LiveData<Boolean> get() = _savingImage
    private val _unsavingImage: MutableLiveData<Boolean> = MutableLiveData()
    val unsavingImage: LiveData<Boolean> get() = _unsavingImage



    private val _savedData: MutableLiveData<LocalRequestState> = MutableLiveData()
    val savedData: LiveData<LocalRequestState> get() = _savedData

    private val _unsaveMode: MutableLiveData<Boolean> = MutableLiveData()
    val unsaveMode: LiveData<Boolean> get() = _unsaveMode
    
    private val _unsaveList: MutableLiveData<List<CuteGeneric>> = MutableLiveData()
    val unsaveList: LiveData<List<CuteGeneric>> get() = _unsaveList
    
    

    fun getRandom() {
        when (Random.nextInt(4)) {
            0 -> getRandomCat()
            1 -> getRandomDog()
            2 -> getRandomDuck()
            3 -> getRandomFox()
        }
    }

    fun getRandomDog() {
        getPicJob?.cancel()
        getPicJob = viewModelScope.launch(ioDispatcher) {
            repositoryDog.getRandomDog()
                .collect { _data.postValue(it) }
        }
    }

    fun getRandomFox() {
        getPicJob?.cancel()
        getPicJob = viewModelScope.launch(ioDispatcher) {
            repositoryFox.getRandomFox()
                .collect { _data.postValue(it) }
        }
    }

    fun getRandomCat() {
        getPicJob?.cancel()
        getPicJob = viewModelScope.launch(ioDispatcher) {
            repositoryCat.getRandomCat()
                .collect { _data.postValue(it) }
        }
    }

    fun getRandomDuck() {
        getPicJob?.cancel()
        getPicJob = viewModelScope.launch(ioDispatcher) {
            repositoryDuck.getRandomDuck()
                .collect { _data.postValue(it) }
        }
    }

    // Instead of adding OnPageChangeListener, it's easier to
    //      manage it manually in this case.
    fun changePage(position: Int) {
        getPicJob?.cancel()
        hasImageJob?.cancel()
        val prevPage: Int = currentPage.value?.second ?: 0
        _currentPage.value = Pair(prevPage, position)
    }

    fun hasImage(url: String) {
        hasImageJob?.cancel()
        hasImageJob = viewModelScope.launch(ioDispatcher) {
            val result: Int = localRepository.hasImage(url)
            _hasImageResult.postValue(result != 0)
        }
    }

    fun saveButtonPressed(cuteGeneric: CuteGeneric, context: Context) {
        if (hasImageResult.value == true)
            AlertDialog.Builder(context)
                .setTitle("Unsaving Pic")
                .setMessage("Are you sure? It looks cute.")
                .setNegativeButton("Nah", null)
                .setPositiveButton("I'm sorry, little one.")
                { _, _ -> unsaveImage(cuteGeneric) }
                .show()
        else
            saveImage(cuteGeneric)
    }

    private fun saveImage(cuteGeneric: CuteGeneric) {
        _savingImage.value = true
        viewModelScope.launch(ioDispatcher) {
            localRepository.saveCute(cuteGeneric.toCuteEntity())
                .collect {
                    _savingImage.postValue(false)
                    _hasImageResult.postValue(true)
                }
        }
    }

    private fun unsaveImage(cuteGeneric: CuteGeneric) {
        _unsavingImage.postValue(true)
        viewModelScope.launch(ioDispatcher) {
            localRepository.unsaveCute(cuteGeneric.toCuteEntity())
                .collect {
                    _unsavingImage.postValue(false)
                    _hasImageResult.postValue(false)
                }
        }
    }
    
    

    fun getLocalData(requesterId: String) {
        viewModelScope.launch(ioDispatcher) {
            localRepository.getAllCute(requesterId)
                .collect{ _savedData.postValue(it) }
        }
    }
    fun getLocalData(requesterId: String, cute_type: String) {
        viewModelScope.launch(ioDispatcher) {
            localRepository.getCuteByType(requesterId, cute_type)
                .collect{ _savedData.postValue(it) }
        }
    }

    fun toggleUnsaveMode(on: Boolean) { _unsaveMode.postValue(on) }

    fun unsaveDataClick(cuteGeneric: CuteGeneric): Boolean =
        unsaveList.value?.let {
            val newList: MutableList<CuteGeneric> = it.toMutableList()
            if (it.contains(cuteGeneric)) {
                newList.remove(cuteGeneric)
                _unsaveList.postValue(newList)
                return false
            } else {
                newList.add(cuteGeneric)
                _unsaveList.postValue(newList)
                return true
            }
        } ?: _unsaveList.postValue(listOf(cuteGeneric)).run { true }

    fun confirmCancelUnsave() {
        _unsaveList.postValue(listOf())
    }

    fun confirmUnsave() {
        viewModelScope.launch(ioDispatcher) {
            localRepository.unsaveCute(unsaveList.value!!.map { it.toCuteEntity() })
                .collect{ _unsaveList.postValue(listOf()) }
        }

    }
}