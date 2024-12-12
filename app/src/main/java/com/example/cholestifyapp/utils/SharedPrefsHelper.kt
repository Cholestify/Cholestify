package com.example.cholestifyapp.utils

import android.content.Context
import android.util.Log
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

    // Menyimpan User ID
    fun saveUserId(userId: Int) {
        sharedPreferences.edit().putInt("userId", userId).apply()
    }

    fun getUserId(): Int {
        return sharedPreferences.getInt("userId", -1) // -1 jika tidak ditemukan
    }

    // Menyimpan profil pengguna
    fun saveUserProfile(profile: UpdateProfileRequest) {
        val editor = sharedPreferences.edit()
        editor.putString("firstName", profile.fullName)
        editor.putString("birthdate", profile.birthdate)
        editor.putInt("height", profile.height)
        editor.putInt("weight", profile.weight)
        editor.putFloat("bmi", profile.bmi.toFloat())
        editor.putString("gender", profile.gender)  // Menyimpan gender
        editor.putString("activityFactor", profile.activityFactor)
        editor.apply()
    }

    fun getUserProfile(): UpdateProfileRequest {
        val fullName = sharedPreferences.getString("firstName", "") ?: ""
        val email = sharedPreferences.getString("email", "") ?: ""
        val birthdate = sharedPreferences.getString("birthdate", "") ?: ""
        val height = sharedPreferences.getInt("height", 0)
        val weight = sharedPreferences.getInt("weight", 0)
        val bmi = sharedPreferences.getFloat("bmi", 0.0f).toDouble()
        val gender = sharedPreferences.getString("gender", "") ?: ""  // Mengambil gender
        val activityFactor = sharedPreferences.getString("activityFactor", "") ?: ""

        return UpdateProfileRequest(
            fullName = fullName,
            birthdate = birthdate,
            height = height,
            weight = weight,
            bmi = bmi,
            email = email,
            gender = gender,
            activityFactor = "Not specified"
        )
    }

    // Fungsi tambahan untuk menyimpan nilai kolesterol
    fun saveCholesterolValue(cholesterolValue: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("cholesterolValue", cholesterolValue)
        // Menggunakan commit() untuk memastikan nilai disimpan segera
        if (editor.commit()) {
            Log.d("SharedPrefsHelper", "Cholesterol value saved: $cholesterolValue")
        } else {
            Log.e("SharedPrefsHelper", "Failed to save cholesterol value")
        }
    }

    // Fungsi tambahan untuk mendapatkan nilai kolesterol
    fun getCholesterolValue(): Int {
        val cholesterolValue =
            sharedPreferences.getInt("cholesterolValue", 0) // Default 0 jika tidak ditemukan
        Log.d("SharedPrefsHelper", "Cholesterol value retrieved: $cholesterolValue")
        return cholesterolValue
    }
}