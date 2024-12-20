package com.example.eventdicoding.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventdicoding.data.response.ListEventsItem
import com.example.eventdicoding.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val eventsLiveData = MutableLiveData<List<ListEventsItem>>()
    private val _MessageLiveData = MutableLiveData<String>()

    fun getEvents(): LiveData<List<ListEventsItem>> = eventsLiveData

    fun getErrorMessage(): LiveData<String> = _MessageLiveData

    fun fetchUpcomingEvents(forceReload: Boolean = false) {
        if (forceReload || eventsLiveData.value == null || eventsLiveData.value!!.isEmpty()) {
            ApiConfig.getApiService().getEvents(1)
                .enqueue(object : Callback<com.example.eventdicoding.data.response.Response> {
                    override fun onResponse(
                        call: Call<com.example.eventdicoding.data.response.Response>,
                        response: Response<com.example.eventdicoding.data.response.Response>
                    ) {
                        if (response.isSuccessful) {
                            val eventList = response.body()?.listEvents?.filterNotNull()
                            eventsLiveData.value = eventList ?: emptyList()
                        } else {
                            _MessageLiveData.value =
                                "Failed to load upcoming events. Please try again."
                        }
                    }

                    override fun onFailure(
                        call: Call<com.example.eventdicoding.data.response.Response>,
                        t: Throwable
                    ) {
                        _MessageLiveData.value =
                            "Network error. Please check your internet connection."
                    }
                })
        }
    }
}
