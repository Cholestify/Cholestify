package com.example.cholestifyapp.ui.register

data class RegisterResponse(
    val error: Boolean,
    val message: String,
    val data: RegisterData?
)

data class RegisterData(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,  // Anda bisa menghapus atau mengganti jika tidak diperlukan
    val birthdate: String?,
    val gender: String?,
    val weight: Float?,
    val height: Float?,
    val activity: String?,
    val createdAt: String?,
    val updatedAt: String?
)