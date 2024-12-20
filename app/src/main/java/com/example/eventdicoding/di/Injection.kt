package com.example.eventdicoding.di

import android.content.Context
import com.example.eventdicoding.data.database.FavoriteEventDatabase
import com.example.eventdicoding.data.repository.FavoriteRepository

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val database = FavoriteEventDatabase.getInstance(context)
        val favoriteDao = database.favoriteEventDao()
        return FavoriteRepository(favoriteDao)
    }
}
