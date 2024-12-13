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
import com.example.cholestifyapp.data.response.Data
import com.example.cholestifyapp.data.response.UserResponse
import com.example.cholestifyapp.data.retrofit.ApiConfig
import com.example.cholestifyapp.databinding.FragmentProfileBinding
import com.example.cholestifyapp.utils.SharedPrefsHelper
import retrofit2.Call
import retrofit2.Response
import java.util.Calendar

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPrefsHelper: SharedPrefsHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        sharedPrefsHelper = SharedPrefsHelper(requireContext())

        fetchUserProfile()
        setupListeners()
        setupActivityFactorDropdown()

        return binding.root
    }

    private fun setupListeners() {
        binding.btnUpdateProfile.setOnClickListener { updateProfile() }
        binding.btnLogout.setOnClickListener { logout() }
        binding.edDateOfBirth.setOnClickListener { showDatePickerDialog(binding.edDateOfBirth) }
    }

    private fun logout() {
        sharedPrefsHelper.clearLoginStatus()
        sharedPrefsHelper.clearToken()
        findNavController().navigate(R.id.loginFragment)
        findNavController().popBackStack(R.id.loginFragment, false)
    }

    private fun getProfileInput(): UpdateProfileRequest? {
        val name = binding.edName.text.toString().trim()
//        val email = binding.tvProfileEmail.text.toString().trim()
        val birthdate = binding.edDateOfBirth.text.toString().trim()
        val gender = binding.edGender.text.toString().trim()
        val weight = binding.edWeight.text.toString().toIntOrNull()
        val height = binding.edHeight.text.toString().toIntOrNull()
        val activity = binding.ActivityFactor.text.toString().trim()

        Log.d("ProfileInput", "Name: $name, Birthdate: $birthdate, Gender: $gender, Weight: $weight, Height: $height, Activity: $activity")

        if (!birthdate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
            Toast.makeText(requireContext(), "Birthdate must be in YYYY-MM-DD format", Toast.LENGTH_SHORT).show()
            return null
        }

        if (height == null || height !in 50..250) {
            Toast.makeText(requireContext(), "Height must be between 50 and 250 cm", Toast.LENGTH_SHORT).show()
            return null
        }

        if (weight == null || weight !in 10..300) {
            Toast.makeText(requireContext(), "Weight must be between 10 and 300 kg", Toast.LENGTH_SHORT).show()
            return null
        }

        return UpdateProfileRequest(name, birthdate, gender, weight, height, activity)
    }


    private fun updateProfile() {
        val token = sharedPrefsHelper.getToken() ?: return
        if (token == null) {
            Log.e("UpdateProfile", "Token is null. Please login again.")
            Toast.makeText(requireContext(), "Authentication failed. Please login again.", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("UpdateProfile", "Token: $token")

        val profileRequest = getProfileInput()

        profileRequest?.let {
            ApiConfig.getApiService().updateProfile("Bearer $token", it).enqueue(
                object : retrofit2.Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            // Profil berhasil diperbarui
                            val userProfile = response.body()!!.data
                            userProfile?.let { updateUI(it) } // Perbarui tampilan dengan data baru

                            // Simpan profil ke Shared Preferences
                            sharedPrefsHelper.saveUserProfile(
                                UpdateProfileRequest(
                                    name = userProfile?.name ?: "",
//                                    email = userProfile?.email ?: "",
                                    birthdate = userProfile?.birthdate ?: "",
                                    gender = userProfile?.gender ?: "",
                                    weight = userProfile?.weight ?: 0,
                                    height = userProfile?.height ?: 0,
                                    activity = userProfile?.activity ?: ""
                                )
                            )

                            // Tampilkan pesan sukses
                            Toast.makeText(requireContext(), "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                        } else {
                            // Gagal memperbarui profil, tampilkan pesan error
                            val errorBody = response.errorBody()?.string()
                            Log.e("UpdateProfile", "Error updating profile: ${response.code()} - $errorBody")
                            Toast.makeText(requireContext(), "Gagal memperbarui profil: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        // Error jaringan atau kegagalan API lainnya
                        Log.e("UpdateProfile", "Network error: ${t.message}")
                        Toast.makeText(requireContext(), "Kesalahan jaringan. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun setupActivityFactorDropdown() {
        val activityFactors = listOf("light", "normal", "hard")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, activityFactors)
        binding.ActivityFactor.setAdapter(adapter)
    }

    private fun fetchUserProfile() {
        val token = sharedPrefsHelper.getToken() ?: return

        // Panggil API untuk mendapatkan data profil
        ApiConfig.getApiService().getUserProfile("Bearer $token").enqueue(
            object : retrofit2.Callback<UserResponse> {
                override fun onResponse(
                    call: retrofit2.Call<UserResponse>,
                    response: retrofit2.Response<UserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        // Data profil berhasil diperoleh dari API
                        val userProfile = response.body()!!.data
                        userProfile?.let { updateUI(it) }

                        // Simpan profil ke SharedPrefsHelper untuk penggunaan offline
                        sharedPrefsHelper.saveUserProfile(
                            UpdateProfileRequest(
                                name = userProfile?.name ?: "",
//                                email = userProfile?.email ?: "",
                                birthdate = userProfile?.birthdate ?: "",
                                gender = userProfile?.gender ?: "",
                                weight = userProfile?.weight ?: 0,
                                height = userProfile?.height ?: 0,
                                activity = userProfile?.activity ?: ""
                            )
                        )
                    } else {
                        // Jika API gagal, gunakan data dari SharedPrefsHelper
                        Log.e("ProfileFragment", "Gagal mendapatkan profil dari server: ${response.errorBody()?.string()}")
                        loadProfileFromCache()
                    }
                }

                override fun onFailure(call: retrofit2.Call<UserResponse>, t: Throwable) {
                    // Jika terjadi kesalahan jaringan, gunakan data dari SharedPrefsHelper
                    Log.e("ProfileFragment", "Kesalahan jaringan: ${t.message}")
                    loadProfileFromCache()
                }
            }
        )
    }

    // Fungsi untuk memuat profil dari SharedPrefsHelper
    private fun loadProfileFromCache() {
        val cachedProfile = sharedPrefsHelper.getUserProfile()
        updateUI(
            Data(
                name = cachedProfile.name,
//                email = cachedProfile.email,
                birthdate = cachedProfile.birthdate,
                gender = cachedProfile.gender,
                weight = cachedProfile.weight,
                height = cachedProfile.height,
                activity = cachedProfile.activity
            )
        )
        Toast.makeText(requireContext(), "Memuat data profil dari cache", Toast.LENGTH_SHORT).show()
    }


    private fun updateUI(userProfile: Data) {
        binding.edName.setText(userProfile?.name ?: "")
//        binding.tvProfileEmail.text = userProfile?.email ?: ""
        binding.edDateOfBirth.setText(userProfile?.birthdate ?: "")
        binding.edGender.setText(userProfile?.gender ?: "")
        binding.edWeight.setText(userProfile?.weight?.toString() ?: "")
        binding.edHeight.setText(userProfile?.height?.toString() ?: "")
        binding.ActivityFactor.setText(userProfile?.activity ?: "")
    }


    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            editText.context, { _, year, month, day ->
                val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, day)
                editText.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}