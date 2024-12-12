package com.example.cholestifyapp.ui.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cholestifyapp.R
import com.example.cholestifyapp.data.response.UserResponse
import com.example.cholestifyapp.data.retrofit.ApiConfig
import com.example.cholestifyapp.databinding.FragmentProfileBinding
import com.example.cholestifyapp.utils.SharedPrefsHelper
import java.util.Calendar
import kotlin.math.pow

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private lateinit var sharedPrefsHelper: SharedPrefsHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        sharedPrefsHelper = SharedPrefsHelper(requireContext())
        fetchUserProfile()

        binding?.btnUpdateProfile?.setOnClickListener {
            updateProfile()
        }

        setupActivityFactorDropdown()
        binding!!.btnLogout.setOnClickListener {
            logout()
        }

        binding?.edDateOfBirth?.setOnClickListener {
            showDatePickerDialog(binding!!.edDateOfBirth)
        }

        return binding!!.root
    }

    private fun logout() {
        Log.d("ProfileFragment", "User logout initiated")
        sharedPrefsHelper.clearLoginStatus()
        sharedPrefsHelper.clearToken()
        findNavController().navigate(R.id.loginFragment)
        findNavController().popBackStack(R.id.loginFragment, false)
        Log.d("ProfileFragment", "User successfully logged out")
    }

    private fun getProfileInput(): UpdateProfileRequest {
        Log.d("ProfileFragment", "Getting profile input from user")
        val birthdate = binding?.edDateOfBirth?.text.toString()
        val height = binding?.edHeight?.text.toString().toIntOrNull() ?: 0
        val weight = binding?.edWeight?.text.toString().toIntOrNull() ?: 0

        if (!birthdate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
            Log.e("ProfileFragment", "Invalid birthdate format: $birthdate")
            Toast.makeText(requireContext(), "Tanggal lahir harus dalam format YYYY-MM-DD", Toast.LENGTH_SHORT).show()
            throw IllegalArgumentException("Invalid birthdate format")
        }

        if (height !in 50..250) {
            Log.e("ProfileFragment", "Invalid height value: $height")
            Toast.makeText(requireContext(), "Tinggi badan harus antara 50 cm dan 250 cm", Toast.LENGTH_SHORT).show()
            throw IllegalArgumentException("Invalid height value")
        }

        if (weight !in 10..300) {
            Log.e("ProfileFragment", "Invalid weight value: $weight")
            Toast.makeText(requireContext(), "Berat badan harus antara 10 kg dan 300 kg", Toast.LENGTH_SHORT).show()
            throw IllegalArgumentException("Invalid weight value")
        }

        return UpdateProfileRequest(
            fullName = binding?.edName?.text.toString(),
<<<<<<< HEAD
            birthdate = birthdate,
            height = height,
            weight = weight,
            bmi = (weight / ((height / 100.0).pow(2))).takeIf { it.isFinite() } ?: 0.0,
            email = binding?.tvProfileEmail?.text.toString(),
            gender = binding?.edGender?.text.toString(),
            activityFactor = binding?.ActivityFactor?.text.toString()
=======
            birthdate = binding?.edDateOfBirth?.text.toString(),
            height = binding?.edHeight?.text.toString().toIntOrNull() ?: 0,
            weight = binding?.edWeight?.text.toString().toIntOrNull() ?: 0,
//            bmi = binding?.edBMI?.text.toString().toDoubleOrNull() ?: 0.0,
            email = binding?.tvProfileEmail?.text.toString(),
            gender = "male"
>>>>>>> 1d8add3 (Update Profile Fix but minus date)
        )
    }

    private fun updateProfile() {
        val token = sharedPrefsHelper.getToken() ?: ""
<<<<<<< HEAD
        val userId = sharedPrefsHelper.getUserId()
        val apiService = ApiConfig.getApiService()
        val profileRequest = getProfileInput()

        Log.d("ProfileFragment", "Starting profile update for user ID: $userId")
        Log.d("ProfileFragment", "Update request body: $profileRequest")

        apiService.updateProfile("Bearer $token", userId, profileRequest).enqueue(object : retrofit2.Callback<UserResponse> {
=======
        val apiService = ApiConfig.getApiService()
        val profileRequest = getProfileInput()

        // Send the update request with the user ID
        apiService.updateProfile("Bearer $token", profileRequest).enqueue(object : retrofit2.Callback<UserResponse> {
>>>>>>> 1d8add3 (Update Profile Fix but minus date)
            override fun onResponse(call: retrofit2.Call<UserResponse>, response: retrofit2.Response<UserResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val userProfile = response.body()!!.data
                    Log.d("ProfileFragment", "Profile update successful: $userProfile")
                    sharedPrefsHelper.saveUserProfile(profileRequest)

                    binding?.apply {
                        tvProfileName.text = userProfile!!.name
                        tvProfileEmail.text = userProfile.email
                        tvBirthdate.text = "Birthdate: ${userProfile.birthdate.split("T")[0]}"
                        tvWeight.text = "Weight: ${userProfile.weight} kg"
                        tvHeight.text = "Height: ${userProfile.height} cm"
                        tvGender.text = "Gender: ${userProfile.gender}"
                    }

                    Toast.makeText(requireContext(), response.body()?.message, Toast.LENGTH_SHORT).show()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ProfileFragment", "Profile update failed: $errorBody")
                    Toast.makeText(requireContext(), "Gagal memperbarui profil: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<UserResponse>, t: Throwable) {
                Log.e("ProfileFragment", "Network error during profile update: ${t.message}")
                Toast.makeText(requireContext(), "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupActivityFactorDropdown() {
        Log.d("ProfileFragment", "Setting up activity factor dropdown")
        val activityFactors = listOf("light", "normal", "hard")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, activityFactors)
        binding?.ActivityFactor?.setAdapter(adapter)
    }

    private fun fetchUserProfile() {
        val token = sharedPrefsHelper.getToken() ?: ""
        val apiService = ApiConfig.getApiService()

        Log.d("ProfileFragment", "Fetching user profile")
        apiService.getUserProfile("Bearer $token").enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(call: retrofit2.Call<UserResponse>, response: retrofit2.Response<UserResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val userProfile = response.body()!!.data
                    Log.d("ProfileFragment", "User profile fetched successfully: $userProfile")
                    sharedPrefsHelper.saveUserId(userProfile!!.id)

                    binding?.apply {
                        tvProfileName.text = userProfile.name
                        tvProfileEmail.text = userProfile.email
                        tvBirthdate.text = "Birthdate: ${userProfile.birthdate.split("T")[0]}"
                        tvWeight.text = "Weight: ${userProfile.weight} kg"
                        tvHeight.text = "Height: ${userProfile.height} cm"
                        tvGender.text = "Gender: ${userProfile.gender}"
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ProfileFragment", "Failed to fetch profile: $errorBody")
                }
            }

            override fun onFailure(call: retrofit2.Call<UserResponse>, t: Throwable) {
                Log.e("ProfileFragment", "Network error during profile fetch: ${t.message}")
            }
        })
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        Log.d("ProfileFragment", "Showing date picker dialog")
        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            editText.setText(formattedDate)
            Log.d("ProfileFragment", "Date selected: $formattedDate")
        }, year, month, day).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("ProfileFragment", "View destroyed")
    }
}
