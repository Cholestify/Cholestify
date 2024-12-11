package com.example.cholestifyapp.ui.profile

data class UpdateProfileRequest(
    val fullName: String,
    val email: String,
//    val lastName: String,
//    val phoneNumber: String,
    val gender: String,
    val birthdate: String,
    val height: Int,
    val weight: Int,
    val bmi: Double,
//    val activityFactor: String
)
