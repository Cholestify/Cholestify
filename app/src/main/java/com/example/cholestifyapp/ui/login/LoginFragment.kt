package com.example.cholestifyapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cholestifyapp.R
import com.example.cholestifyapp.data.retrofit.ApiService
import com.example.cholestifyapp.data.retrofit.RetrofitClient
import com.example.cholestifyapp.utils.SharedPrefsHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var sharedPrefsHelper: SharedPrefsHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        // Inisialisasi view
        emailEditText = rootView.findViewById(R.id.editTextTextEmailAddress)
        passwordEditText = rootView.findViewById(R.id.editTextTextPassword)
        loginButton = rootView.findViewById(R.id.buttonlogin)
        registerButton = rootView.findViewById(R.id.buttonregist) // Pastikan ID ini sesuai dengan layout XML

        // Inisialisasi SharedPrefsHelper
        sharedPrefsHelper = SharedPrefsHelper(requireContext())

        // Tombol Login
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(activity, "Email and password are required", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }

        // Tombol Register
        registerButton.setOnClickListener {
            // Pindah ke RegisterFragment
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return rootView
    }

    private fun loginUser(email: String, password: String) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val request = LoginRequest(email, password)
        val call = apiService.login(request)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()
                    if (loginResponse != null && !loginResponse.error) {
                        // Login berhasil, simpan token ke SharedPreferences
                        val token = loginResponse.data?.token ?: ""
                        sharedPrefsHelper.saveToken(token)

                        // Menampilkan Toast untuk sukses
                        Toast.makeText(activity, loginResponse.message, Toast.LENGTH_SHORT).show()

                        // Pindahkan pengguna ke halaman utama
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(activity, "Login failed: ${loginResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(activity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(activity, "Failed to connect to the server", Toast.LENGTH_SHORT).show()
            }
        })
    }
}