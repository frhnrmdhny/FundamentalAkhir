package com.example.eventdicoding.ui.favorite


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventdicoding.data.database.FavoriteEvent
import com.example.eventdicoding.data.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {


    fun getAllFavorites(): LiveData<List<FavoriteEvent>> {
        return repository.getAllFavorites()
    }


    fun insertFavorite(event: FavoriteEvent) {
        viewModelScope.launch {
            repository.insertFavorite(event)
        }
    }


    fun deleteFavorite(event: FavoriteEvent) {
        viewModelScope.launch {
            repository.deleteFavorite(event)
        }
    }
}
