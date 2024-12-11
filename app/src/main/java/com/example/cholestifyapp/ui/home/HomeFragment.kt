package com.example.cholestifyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cholestifyapp.R
import com.example.cholestifyapp.data.response.NutritionData
import com.example.cholestifyapp.data.retrofit.ApiConfig
import com.example.cholestifyapp.databinding.FragmentHomeBinding
import com.example.cholestifyapp.utils.SharedPrefsHelper
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Listener untuk tombol Update Daily Food
        binding.updateDailyFood.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_updateDailyFragment)
        }

        // Memanggil API untuk mendapatkan data
        fetchDailyNutrition()
        fetchFoodRecommendations()

        return binding.root
    }

    private fun fetchDailyNutrition() {
        val sharedPrefsHelper = SharedPrefsHelper(requireContext())
        val token = sharedPrefsHelper.getToken() ?: ""
        if (token.isEmpty()) {
            showError("Token is missing. Please log in again.")
            return
        }

        val authorizationHeader = "Bearer $token"
        lifecycleScope.launch {
            try {
                val response = ApiConfig.getApiService().getDailyNutrition(authorizationHeader)
                if (!response.error) {
                    updateNutritionUI(response.data)
                } else {
                    showError("Failed to fetch daily nutrition: ${response.message}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showError("An error occurred while fetching daily nutrition")
            }
        }
    }

    private fun fetchFoodRecommendations() {
        lifecycleScope.launch {
            try {
                val response = ApiConfig.getApiService().getFoodRecommendations()
                if (!response.error) {
                    // Menampilkan data rekomendasi ke UI
                    val foodList = response.data
                    binding.textViewRecom1.text = foodList.getOrNull(0)?.food ?: "No Recommendation"
                    binding.textViewRecom2.text = foodList.getOrNull(1)?.food ?: "No Recommendation"
                    binding.textViewRecom3.text = foodList.getOrNull(2)?.food ?: "No Recommendation"
                } else {
                    showError("Failed to fetch recommendations: ${response.message}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showError("An error occurred while fetching recommendations")
            }
        }
    }

    private fun updateNutritionUI(data: NutritionData) {
        binding.textViewProtein.text = String.format("%dmg\nProtein", data.totalProtein.toInt())
        binding.textViewFat.text = String.format("%dmg\nFat", data.totalFat.toInt())
        binding.textViewCarbo.text = String.format("%dmg\nCarbo", data.totalCarbohydrate.toInt())
        binding.textViewCalories.text = String.format("%dmg\nTotal Cal", data.totalCalories.toInt())
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}