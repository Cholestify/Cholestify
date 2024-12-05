package com.example.cholestifyapp.data.retrofit


import com.example.cholestifyapp.data.response.ResponseFood
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("food/{id}")
    fun getFood(
        @Path("id") id: Int,
    ): Call<ResponseFood>
}