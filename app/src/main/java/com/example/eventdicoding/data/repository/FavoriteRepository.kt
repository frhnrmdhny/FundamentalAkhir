package com.example.eventdicoding.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.eventdicoding.data.database.FavoriteEvent
import com.example.eventdicoding.data.database.FavoriteEventDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteRepository(private val favoriteEventDao: FavoriteEventDao) {

    fun getAllFavorites(): LiveData<List<FavoriteEvent>> = favoriteEventDao.getAllFavoriteEvents()

    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent?> = liveData {
        val favorite = favoriteEventDao.getFavoriteEventById(id)
        emit(favorite)
    }

    fun insertFavorite(event: FavoriteEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteEventDao.insertFavoriteEvent(event)
        }
    }

    fun deleteFavorite(event: FavoriteEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteEventDao.deleteFavoriteEvent(event)
        }
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null

        fun getInstance(favoriteEventDao: FavoriteEventDao): FavoriteRepository {
            return instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favoriteEventDao).also { instance = it }
            }
        }
    }
}
