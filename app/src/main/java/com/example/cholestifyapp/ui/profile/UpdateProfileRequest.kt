package com.example.cholestifyapp.ui.profile

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @field:SerializedName("name")
    val name: String,

//    @field:SerializedName("email")
//    val email: String,

    @field:SerializedName("birthdate")
    val birthdate: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("weight")
    val weight: Int,

    @field:SerializedName("height")
    val height: Int,

    @field:SerializedName("activity")
    val activity: String
)
