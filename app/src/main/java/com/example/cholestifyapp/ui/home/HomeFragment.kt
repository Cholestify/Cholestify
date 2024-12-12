package com.example.cholestifyapp.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cholestifyapp.R
import com.example.cholestifyapp.data.response.FoodNutritionData
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

        // Listener untuk tombol check nilai kolesterol
        binding.buttonIYV.setOnClickListener {
            val inputText = binding.inputCholesterol.text.toString()
            if (inputText.isNotEmpty()) {
                val cholesterolValue = inputText.toInt()
                updateCholesterolStatus(cholesterolValue)
            } else {
                showError("Please enter a valid number")
            }
        }

        // Memanggil API untuk mendapatkan data
        fetchDailyNutrition()
        fetchFoodRecommendations()
        fetchMealFoodNutrition()
        fetchMeals()

        return binding.root
    }

    private fun updateCholesterolStatus(cholesterolValue: Int) {
        when {
            cholesterolValue < 200 -> {
                // Mengubah teks dan warna untuk kondisi HEALTHY
                binding.dangerText.text = "HEALTHY"
                binding.dangerText.setTextColor(Color.parseColor("#00FF00")) // Hijau
                binding.warningText.text = "You are healthy now, keep it up"
            }
            cholesterolValue in 200..239 -> {
                // Mengubah teks dan warna untuk kondisi AT RISK
                binding.dangerText.text = "AT RISK"
                binding.dangerText.setTextColor(Color.parseColor("#FFC107")) // Kuning
                binding.warningText.text = "You are at risk, watch your cholesterol intake"
            }
            cholesterolValue >= 240 -> {
                // Mengubah teks dan warna untuk kondisi DANGEROUS
                binding.dangerText.text = "DANGEROUS"
                binding.dangerText.setTextColor(Color.parseColor("#FF0000")) // Merah
                binding.warningText.text = "Your shouldn't consume cholesterol > 300 mg"
            }
        }
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

    private fun fetchMeals() {
        val sharedPrefsHelper = SharedPrefsHelper(requireContext())
        val token = sharedPrefsHelper.getToken() ?: ""
        if (token.isEmpty()) {
            showError("Token is missing. Please log in again.")
            return
        }

        val authorizationHeader = "Bearer $token"
        lifecycleScope.launch {
            try {
                val response = ApiConfig.getApiService().getMeals(authorizationHeader)
                Log.d("HomeFragment", "API Response: $response")

                if (!response.error) {
                    // Jika response tidak error, tampilkan data meal ke UI
                    val meals = response.data
                    binding.food1.text = meals.getOrNull(0)?.name ?: "No Meal"
                    binding.food2.text = meals.getOrNull(1)?.name ?: "No Meal"
                    binding.food3.text = meals.getOrNull(2)?.name ?: "No Meal"
                } else {
                    showError("Failed to fetch meals: ${response.message}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showError("An error occurred while fetching meals")
            }
        }
    }

    private fun fetchMealFoodNutrition() {
        val sharedPrefsHelper = SharedPrefsHelper(requireContext())
        val token = sharedPrefsHelper.getToken() ?: ""
        if (token.isEmpty()) {
            showError("Token is missing. Please log in again.")
            return
        }

        val authorizationHeader = "Bearer $token"
        lifecycleScope.launch {
            try {
                val response = ApiConfig.getApiService().getMealFoodNutrition(authorizationHeader)
                if (!response.error) {
                    // Log data yang diterima
                    Log.d("HomeFragment", "Response Data: ${response.data}")
                    updateNutritionUI(response.data)
                } else {
                    showError("Failed to fetch nutrition data: ${response.message}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showError("An error occurred while fetching nutrition data")
            }
        }
    }

    private fun updateNutritionUI(data: FoodNutritionData) {
        // Tambahkan log untuk melihat apakah data sudah diterima dengan benar
        Log.d(
            "HomeFragment",
            "Calories: ${data.calories}, Protein: ${data.protein}, Fat: ${data.fat}, Carbohydrate: ${data.carbohydrate}"
        )

        // Periksa apakah data ada, jika tidak tampilkan "N/A" atau angka 0
        binding.textViewFoodRecordCarbohydrate.text =
            String.format("Carbohydrate: %.1fg", data.carbohydrate.takeIf { it != 0f } ?: 0f)
        binding.textViewFoodRecordProtein.text =
            String.format("Protein: %.1fg", data.protein.takeIf { it != 0f } ?: 0f)
        binding.textViewFoodRecordFat.text =
            String.format("Fat: %.1fg", data.fat.takeIf { it != 0f } ?: 0f)
        binding.textViewFoodRecordCalories.text =
            String.format("Calories: %.1fkcal", data.calories.takeIf { it != 0f } ?: 0f)
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}