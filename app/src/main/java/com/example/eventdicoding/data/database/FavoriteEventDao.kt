package com.example.eventdicoding.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteEventDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteEvent(event: FavoriteEvent)


    @Delete
    suspend fun deleteFavoriteEvent(event: FavoriteEvent)


    @Update
    suspend fun updateFavoriteEvent(event: FavoriteEvent)


    @Query("SELECT * FROM favorite_event WHERE id = :id")
    suspend fun getFavoriteEventById(id: String): FavoriteEvent?


    @Query("SELECT * FROM favorite_event WHERE id = :id")
    fun getFavoriteEventByIdLive(id: String): LiveData<FavoriteEvent?>


    @Query("SELECT * FROM favorite_event")
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>>

    @Query("DELETE FROM favorite_event")
    suspend fun deleteAllFavoriteEvents()
}