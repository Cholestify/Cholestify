package com.example.cholestifyapp.ui.login

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val data: LoginData?
)

data class LoginData(
    val token: String,
    val userId: Int
)