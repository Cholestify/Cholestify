package com.example.cholestifyapp.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("birthdate")
	val birthdate: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("activity")
	val activity: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("weight")
	val weight: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
