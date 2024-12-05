package com.example.cholestifyapp.data.response

import com.google.gson.annotations.SerializedName

data class ResponseFood(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("potassium")
	val potassium: Any? = null,

	@field:SerializedName("manganese")
	val manganese: Int? = null,

	@field:SerializedName("vitaminB1")
	val vitaminB1: Any? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("vitaminB2")
	val vitaminB2: Any? = null,

	@field:SerializedName("vitaminB3")
	val vitaminB3: Any? = null,

	@field:SerializedName("dietaryFiber")
	val dietaryFiber: Int? = null,

	@field:SerializedName("selenium")
	val selenium: Int? = null,

	@field:SerializedName("vitaminB5")
	val vitaminB5: Int? = null,

	@field:SerializedName("vitaminB6")
	val vitaminB6: Any? = null,

	@field:SerializedName("protein")
	val protein: Any? = null,

	@field:SerializedName("fat")
	val fat: Any? = null,

	@field:SerializedName("saturatedFats")
	val saturatedFats: Any? = null,

	@field:SerializedName("polyunsaturatedFats")
	val polyunsaturatedFats: Int? = null,

	@field:SerializedName("cholesterol")
	val cholesterol: Int? = null,

	@field:SerializedName("copper")
	val copper: Any? = null,

	@field:SerializedName("vitaminB12")
	val vitaminB12: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("vitaminB11")
	val vitaminB11: Any? = null,

	@field:SerializedName("zinc")
	val zinc: Any? = null,

	@field:SerializedName("phosphorus")
	val phosphorus: Any? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: Any? = null,

	@field:SerializedName("carbohydrates")
	val carbohydrates: Any? = null,

	@field:SerializedName("sugars")
	val sugars: Int? = null,

	@field:SerializedName("calcium")
	val calcium: Any? = null,

	@field:SerializedName("vitaminC")
	val vitaminC: Any? = null,

	@field:SerializedName("vitaminE")
	val vitaminE: Any? = null,

	@field:SerializedName("monounsaturatedFats")
	val monounsaturatedFats: Any? = null,

	@field:SerializedName("vitaminD")
	val vitaminD: Int? = null,

	@field:SerializedName("magnesium")
	val magnesium: Any? = null,

	@field:SerializedName("vitaminK")
	val vitaminK: Any? = null,

	@field:SerializedName("nutritionDensity")
	val nutritionDensity: Any? = null,

	@field:SerializedName("water")
	val water: Any? = null,

	@field:SerializedName("food")
	val food: String? = null,

	@field:SerializedName("sodium")
	val sodium: Any? = null,

	@field:SerializedName("caloricValue")
	val caloricValue: Int? = null,

	@field:SerializedName("iron")
	val iron: Any? = null,

	@field:SerializedName("vitaminA")
	val vitaminA: Any? = null
)
