package com.example.cholestifyapp.data.response

data class IHaveEatenResponse(
    val error: Boolean,
    val message: String,
    val data: List<Meal>
)

data class Meal(
    val id: Int,
    val userId: Int,
    val foodId: Int,
    val name: String,
    val type: String,
    val time: String,
    val createdAt: String,
    val updatedAt: String
)