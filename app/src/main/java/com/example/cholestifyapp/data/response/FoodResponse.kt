package com.example.cholestifyapp.data.response

import com.google.gson.annotations.SerializedName

data class FoodResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("potassium")
	val potassium: Any,

	@field:SerializedName("manganese")
	val manganese: Any,

	@field:SerializedName("vitaminB1")
	val vitaminB1: Any,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("vitaminB2")
	val vitaminB2: Any,

	@field:SerializedName("vitaminB3")
	val vitaminB3: Any,

	@field:SerializedName("dietaryFiber")
	val dietaryFiber: Double,

	@field:SerializedName("selenium")
	val selenium: Any,

	@field:SerializedName("vitaminB5")
	val vitaminB5: Double,

	@field:SerializedName("vitaminB6")
	val vitaminB6: Any,

	@field:SerializedName("protein")
	val protein: Any,

	@field:SerializedName("fat")
	val fat: Any,

	@field:SerializedName("saturatedFats")
	val saturatedFats: Any,

	@field:SerializedName("polyunsaturatedFats")
	val polyunsaturatedFats: Double,

	@field:SerializedName("cholesterol")
	val cholesterol: Any,

	@field:SerializedName("copper")
	val copper: Any,

	@field:SerializedName("vitaminB12")
	val vitaminB12: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("vitaminB11")
	val vitaminB11: Any,

	@field:SerializedName("zinc")
	val zinc: Any,

	@field:SerializedName("phosphorus")
	val phosphorus: Any,

	@field:SerializedName("updatedAt")
	val updatedAt: Any,

	@field:SerializedName("carbohydrates")
	val carbohydrates: Any,

	@field:SerializedName("sugars")
	val sugars: Double?,

	@field:SerializedName("calcium")
	val calcium: Any,

	@field:SerializedName("vitaminC")
	val vitaminC: Any,

	@field:SerializedName("vitaminE")
	val vitaminE: Any,

	@field:SerializedName("monounsaturatedFats")
	val monounsaturatedFats: Any,

	@field:SerializedName("vitaminD")
	val vitaminD: Double,

	@field:SerializedName("magnesium")
	val magnesium: Any,

	@field:SerializedName("vitaminK")
	val vitaminK: Any,

	@field:SerializedName("nutritionDensity")
	val nutritionDensity: Any,

	@field:SerializedName("water")
	val water: Any,

	@field:SerializedName("food")
	val food: String,

	@field:SerializedName("sodium")
	val sodium: Any,

	@field:SerializedName("caloricValue")
	val caloricValue: Int,

	@field:SerializedName("iron")
	val iron: Any,

	@field:SerializedName("vitaminA")
	val vitaminA: Any
)
