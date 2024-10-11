package com.example.fetchexcercise.retrofit

import com.example.fetchexcercise.data.HiringItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    fun getItems(): Call<List<HiringItem>>
}