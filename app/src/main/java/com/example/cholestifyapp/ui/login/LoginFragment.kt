package com.example.cholestifyapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cholestifyapp.R
import com.example.cholestifyapp.data.retrofit.ApiService
import com.example.cholestifyapp.data.retrofit.RetrofitClient
import com.example.cholestifyapp.databinding.FragmentLoginBinding
import com.example.cholestifyapp.utils.SharedPrefsHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPrefsHelper: SharedPrefsHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Initialize SharedPrefsHelper
        sharedPrefsHelper = SharedPrefsHelper(requireContext())

        // Login button
        binding.buttonlogin.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showToast("Email and password are required")
            } else {
                loginUser (email, password)
            }
        }

        // Register button
        binding.buttonregist.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        // Handle back button press
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Exit the app
            requireActivity().finishAffinity()
        }

        return binding.root
    }

    private fun loginUser (email: String, password: String) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val request = LoginRequest(email, password)
        val call = apiService.login(request)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()
                    if (loginResponse != null && !loginResponse.error) {
                        val token = loginResponse.data?.token ?: ""
                        sharedPrefsHelper.saveToken(token)
                        showToast(loginResponse.message)

                        val idUser = loginResponse.data?.userId ?: ""
                        sharedPrefsHelper.saveUserId(idUser as Int)


                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        showToast("Login failed: ${loginResponse?.message}")
                    }
                } else {
                    showToast("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showToast("Failed to connect to the server")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}