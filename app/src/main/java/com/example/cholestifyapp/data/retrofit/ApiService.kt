package com.example.cholestifyapp.data.retrofit


import com.example.cholestifyapp.data.response.FoodResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("food")
    fun getAllFood(): Call<FoodResponse>
}