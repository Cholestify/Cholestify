package com.example.cholestifyapp.data.retrofit


import com.example.cholestifyapp.data.response.DailyNutritionResponse
import com.example.cholestifyapp.data.response.FoodRecordResponse
import com.example.cholestifyapp.data.response.FoodResponse
import com.example.cholestifyapp.data.response.IHaveEatenResponse
import com.example.cholestifyapp.data.response.UserResponse
import com.example.cholestifyapp.ui.login.LoginRequest
import com.example.cholestifyapp.ui.login.LoginResponse
import com.example.cholestifyapp.ui.profile.UpdateProfileRequest
import com.example.cholestifyapp.ui.register.RegisterRequest
import com.example.cholestifyapp.ui.register.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("food")
    fun getAllFood(): Call<FoodResponse>

    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @PUT("users/profile")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body profile: UpdateProfileRequest
    ): Call<UserResponse>

    @GET("users")
    fun getUserProfile(
        @Header("Authorization") token: String
    ): Call<UserResponse>

    @GET("food/recommendation")
    suspend fun getFoodRecommendations(): FoodResponse

    @GET("users/daily-nutrition")
    suspend fun getDailyNutrition(
        @Header("Authorization") token: String
    ): DailyNutritionResponse

    @GET("mealFood/recentNutritionHistory")
    suspend fun getMealFoodNutrition(
        @Header("Authorization") token: String
    ): FoodRecordResponse

    @GET("mealFood")
    suspend fun getMeals(
        @Header("Authorization") token: String  // Menambahkan header Authorization
    ): IHaveEatenResponse

}