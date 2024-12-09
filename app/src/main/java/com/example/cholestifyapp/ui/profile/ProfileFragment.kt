package com.example.cholestifyapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // Tampilkan data profil saat pertama kali fragment dimuat
        loadUserProfile()

        binding?.btnUpdateProfile?.setOnClickListener { updateProfile() }

        // Menampilkan data pengguna (contoh)
        binding!!.ivProfilePicture.setImageResource(R.drawable.ic_person)
        binding!!.tvProfileName.text = "lOREM IPSUM 53"
        binding!!.tvProfileEmail.text = "LOREM IPSUM 53"
        binding!!.tvPersonalInformation.text = "Personal Information"
        binding!!.edName.hint = ""
        binding!!.edPhoneNumber.hint = ""
        binding!!.edDateOfBirth.hint = ""
        binding!!.tvPersonalNutrition.text = "Personal Nutrition"
        binding!!.edHeight.hint = ""
        binding!!.edWeight.hint = ""
        binding!!.edBMI.hint = ""
        binding!!.ActivityFactor.hint = ""
        binding!!.btnUpdateProfile.text = "Update Profile"

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
                    sharedPrefsHelper.saveUserProfile(profileRequest)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}