package com.example.cholestifyapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cholestifyapp.R
import com.example.cholestifyapp.data.response.UserResponse
import com.example.cholestifyapp.data.retrofit.ApiConfig
import com.example.cholestifyapp.databinding.FragmentProfileBinding
import com.example.cholestifyapp.utils.SharedPrefsHelper

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private lateinit var sharedPrefsHelper: SharedPrefsHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Inisialisasi SharedPrefsHelper
        sharedPrefsHelper = SharedPrefsHelper(requireContext())

        // Panggil API untuk mendapatkan profil pengguna
        fetchUserProfile()

        // Tampilkan data profil saat pertama kali fragment dimuat
        loadUserProfile()

        binding?.btnUpdateProfile?.setOnClickListener { updateProfile() }


        // Tombol Logout
        binding!!.btnLogout.setOnClickListener {
            logout()
        }

        return binding!!.root
    }

    private fun logout() {
        // Menghapus status login dan token
        sharedPrefsHelper.clearLoginStatus()
        sharedPrefsHelper.clearToken()

        // Navigasi ke LoginFragment setelah logout
        val navController = findNavController()
        navController.navigate(R.id.loginFragment) // Menavigasi langsung ke LoginFragment

        // Menghapus semua fragment di back stack
        navController.popBackStack(R.id.loginFragment, false)
    }

    private fun getProfileInput(): UpdateProfileRequest {
        return UpdateProfileRequest(
            fullName = binding!!.edName.text.toString(),
            birthdate = binding!!.edDateOfBirth.text.toString(),
            height = binding!!.edHeight.text.toString().toIntOrNull() ?: 0,
            weight = binding!!.edWeight.text.toString().toIntOrNull() ?: 0,
            bmi = binding!!.edBMI.text.toString().toDoubleOrNull() ?: 0.0,
        )
    }

    private fun updateProfile() {
        val apiService = ApiConfig.getApiService()
        val profileRequest = getProfileInput()

        apiService.updateProfile(profileRequest).enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(call: retrofit2.Call<UserResponse>, response: retrofit2.Response<UserResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!.data

                    // Simpan profil yang diperbarui
                    sharedPrefsHelper.saveUserProfile(profileRequest
                    )

                    // Perbarui UI
                    loadUserProfile()
                    Log.d("Profile Update", "Berhasil diperbarui")
                } else {
                    Log.e("Profile Update", "Gagal diperbarui: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<UserResponse>, t: Throwable) {
                Log.e("Profile Update", "Kesalahan jaringan: ${t.message}")
            }
        })
    }

    private fun loadUserProfile() {
        val userProfile = sharedPrefsHelper.getUserProfile()

        binding?.apply {
            tvProfileName.text = userProfile.fullName
            tvProfileEmail.text = userProfile.fullName

            // Personal Information
            edName.setText(userProfile.fullName)
            edDateOfBirth.setText(userProfile.birthdate)

            // Personal Nutrition
            edHeight.setText(userProfile.height.toString())
            edWeight.setText(userProfile.weight.toString())
            edBMI.setText(userProfile.bmi.toString())
        }
    }

    private fun fetchUserProfile() {
        val token = sharedPrefsHelper.getToken() ?: ""
        val apiService = ApiConfig.getApiService()

        apiService.getUserProfile("Bearer $token").enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(
                call: retrofit2.Call<UserResponse>,
                response: retrofit2.Response<UserResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val userProfile = response.body()!!.data

                    // Simpan ID pengguna ke SharedPreferences
                    sharedPrefsHelper.saveUserId(userProfile.id)

                    val userId = sharedPrefsHelper.getUserId()
                    Log.d("User ID", "ID Pengguna: $userId")

                    Toast.makeText(requireContext(), "ID Pengguna: ${userProfile.id}", Toast.LENGTH_SHORT).show()


                    // Tampilkan data di UI
                    binding?.apply {
                        tvProfileName.text = userProfile.name
                        tvProfileEmail.text = userProfile.email
                    }

                    Log.d("UserProfile", "ID: ${userProfile.id}")
                    Log.d("UserProfile", "Nama: ${userProfile.name}")

                } else {
                    Log.e("UserProfile", "Gagal mendapatkan profil: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<UserResponse>, t: Throwable) {
                Log.e("UserProfile", "Kesalahan jaringan: ${t.message}")
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}