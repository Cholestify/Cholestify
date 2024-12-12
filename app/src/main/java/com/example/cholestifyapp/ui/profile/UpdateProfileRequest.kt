package com.example.cholestifyapp.ui.profile

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @field:SerializedName("name")
    val fullName: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("gender")
    val gender: String,
    @field:SerializedName("birthdate")
    val birthdate: String,
    @field:SerializedName("height")
    val height: Int,
    @field:SerializedName("weight")
    val weight: Int,
<<<<<<< HEAD
    @field:SerializedName("bmi")
    val bmi: Double,
    @field:SerializedName("activity")
    val activityFactor: String
=======
//    val bmi: Double,
//    val activityFactor: String
>>>>>>> 1d8add3 (Update Profile Fix but minus date)
)
