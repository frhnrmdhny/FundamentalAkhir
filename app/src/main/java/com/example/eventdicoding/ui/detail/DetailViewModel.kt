package com.example.eventdicoding.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventdicoding.data.database.FavoriteEvent
import com.example.eventdicoding.data.repository.FavoriteRepository
import com.example.eventdicoding.data.response.DetailResponse
import com.example.eventdicoding.data.response.Event
import com.example.eventdicoding.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(
    private val repository: FavoriteRepository
) : ViewModel() {

    private val _event = MutableLiveData<Event?>()
    val event: LiveData<Event?> get() = _event

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun fetchEventDetail(eventId: Int) {
        _isLoading.value = true
        ApiConfig.getApiService().getEventDetail(eventId)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val event = response.body()?.event
                        if (event != null) {
                            _event.value = event
                            checkIfFavorite(event.id.toString())
                        } else {
                            Log.e("DetailViewModel", "Event is null")
                            _event.value = null
                        }
                    } else {
                        Log.e(
                            "DetailViewModel",
                            "Failed to get event details: ${response.message()}"
                        )
                        _event.value = null
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("DetailViewModel", "API call failed: ${t.message}")
                    _event.value = null
                    t.printStackTrace()
                }
            })
    }

    fun checkIfFavorite(eventId: String) {
        repository.getFavoriteEventById(eventId).observeForever { favorite ->
            _isFavorite.value = favorite != null
        }
    }

    fun toggleFavorite() {
        val currentEvent = _event.value
        if (currentEvent != null) {
            if (_isFavorite.value == true) {
                repository.deleteFavorite(
                    FavoriteEvent(
                        id = currentEvent.id.toString(),
                        name = currentEvent.name ?: "",
                        mediaCover = currentEvent.mediaCover
                    )
                )
            } else {
                repository.insertFavorite(
                    FavoriteEvent(
                        id = currentEvent.id.toString(),
                        name = currentEvent.name ?: "",
                        mediaCover = currentEvent.mediaCover
                    )
                )
            }
            _isFavorite.value = _isFavorite.value != true
        }
    }
}


