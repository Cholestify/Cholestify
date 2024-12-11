package com.example.cholestifyapp.data.response

data class DailyNutritionResponse(
    val error: Boolean,
    val message: String,
    val data: NutritionData
)

data class NutritionData(
    val totalCalories: Double,
    val totalProtein: Double,
    val totalFat: Double,
    val totalCarbohydrate: Double
)