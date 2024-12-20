package com.example.eventdicoding.data.retrofit

import com.example.eventdicoding.data.response.DetailResponse
import com.example.eventdicoding.data.response.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(@Query("active") active: Int): Call<Response>

    @GET("events/{id}")
    fun getEventDetail(@Path("id") id: Int): Call<DetailResponse>
}
