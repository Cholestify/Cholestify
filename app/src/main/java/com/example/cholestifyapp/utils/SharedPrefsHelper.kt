package com.example.cholestifyapp.utils

import android.content.Context
import com.example.cholestifyapp.ui.profile.UpdateProfileRequest

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



    fun saveUserProfile(profile: UpdateProfileRequest) {
        val editor = sharedPreferences.edit()
        editor.putString("firstName", profile.fullName)
//        editor.putString("lastName", profile.lastName)
//        editor.putString("phoneNumber", profile.phoneNumber)
        editor.putString("birthdate", profile.birthdate)
        editor.putInt("height", profile.height)
        editor.putInt("weight", profile.weight)
        editor.putFloat("bmi", profile.bmi.toFloat())
        editor.apply()
    }

    fun getUserProfile(): UpdateProfileRequest {
        val fullName = sharedPreferences.getString("firstName", "") ?: ""
        val birthdate = sharedPreferences.getString("birthdate", "") ?: ""
        val height = sharedPreferences.getInt("height", 0)
        val weight = sharedPreferences.getInt("weight", 0)
        val bmi = sharedPreferences.getFloat("bmi", 0.0f).toDouble()

        return UpdateProfileRequest(
            fullName = fullName,
            birthdate = birthdate,
            height = height,
            weight = weight,
            bmi = bmi
        )
    }




}