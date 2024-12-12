package com.example.cholestifyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
                // Tampilkan ProgressBar saat tombol ditekan
                showLoading(true)
                loginUser(email, password)
            }
        }

        // Register button
        binding.buttonregist.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        playAnimation()

        // Handle back button press
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Exit the app
            requireActivity().finishAffinity()
        }

        return binding.root
    }

    private fun loginUser(email: String, password: String) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val request = LoginRequest(email, password)

        // Tampilkan ProgressBar
        showLoading(true)

        val call = apiService.login(request)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                // Sembunyikan ProgressBar
                showLoading(false)

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
                // Sembunyikan ProgressBar
                showLoading(false)

                showToast("Failed to connect to the server")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
        // Nonaktifkan tombol login selama proses berjalan
        binding.buttonlogin.isEnabled = !isLoading
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logocoles, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 2200
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.buttonlogin, View.ALPHA, 1f).setDuration(90)

        val together = AnimatorSet().apply {
            playTogether(login)
        }

        AnimatorSet().apply {
            playSequentially(together)
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}