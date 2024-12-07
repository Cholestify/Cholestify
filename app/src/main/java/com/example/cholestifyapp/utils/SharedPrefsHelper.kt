package com.example.cholestifyapp.utils

import android.content.Context

class SharedPrefsHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    // Menyimpan status login
    fun setLoginStatus(status: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", status).apply()
    }

    // Mendapatkan status login
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    // Menyimpan token autentikasi
    fun saveToken(token: String) {
        sharedPreferences.edit().putString("authToken", token).apply()
    }

    // Mendapatkan token autentikasi
    fun getToken(): String? {
        return sharedPreferences.getString("authToken", null)
    }

    // Menghapus token (digunakan untuk logout)
    fun clearToken() {
        sharedPreferences.edit().remove("authToken").apply()
    }

    // Menghapus status login (digunakan untuk logout)
    fun clearLoginStatus() {
        sharedPreferences.edit().remove("isLoggedIn").apply()
    }
}