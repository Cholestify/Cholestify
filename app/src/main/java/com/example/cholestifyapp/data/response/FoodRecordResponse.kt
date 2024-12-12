package com.example.cholestifyapp.data.response

data class FoodRecordResponse(
    val error: Boolean,
    val message: String,
    val data: FoodNutritionData
)

data class FoodNutritionData(
    val calories: Float,
    val protein: Float,
    val fat: Float,
    val carbohydrate: Float
)