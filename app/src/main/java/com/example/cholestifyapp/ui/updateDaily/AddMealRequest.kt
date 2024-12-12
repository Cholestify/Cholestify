package com.example.cholestifyapp.data.request

data class AddMealRequest(
    val id: Int,
    val food: String?,
    var isSelected: Boolean = false
)

