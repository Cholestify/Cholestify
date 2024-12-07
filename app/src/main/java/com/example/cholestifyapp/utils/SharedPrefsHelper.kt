package com.example.cholestifyapp.utils

import android.content.Context

class SharedPrefsHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun setLoginStatus(status: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", status).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("authToken", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("authToken", null)
    }
}