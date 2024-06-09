package com.xc.brainstore.view.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xc.brainstore.data.local.entity.FavoriteProduct
import com.xc.brainstore.data.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    private val _allFavorites = MediatorLiveData<List<FavoriteProduct>>()
    val allFavorites: LiveData<List<FavoriteProduct>> = _allFavorites

    init {
        _allFavorites.addSource(favoriteRepository.getAllFavorites()) { favorites ->
            _allFavorites.value = favorites
        }
    }

    fun insert(favoriteProduct: FavoriteProduct) {
        viewModelScope.launch {
            favoriteRepository.insert(favoriteProduct)
        }
    }

    fun delete(favoriteProduct: FavoriteProduct) {
        viewModelScope.launch {
            favoriteRepository.delete(favoriteProduct)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            favoriteRepository.deleteAll()
        }
    }

    fun getFavoriteProductById(id: Int): LiveData<FavoriteProduct?> {
        return favoriteRepository.getFavoriteProductById(id)
    }
}