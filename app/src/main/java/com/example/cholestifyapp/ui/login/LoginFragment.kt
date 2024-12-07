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
    private lateinit var sharedPrefsHelper: SharedPrefsHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        emailEditText = rootView.findViewById(R.id.editTextTextEmailAddress)
        passwordEditText = rootView.findViewById(R.id.editTextTextPassword)
        loginButton = rootView.findViewById(R.id.button)

        // Inisialisasi SharedPrefsHelper
        sharedPrefsHelper = SharedPrefsHelper(requireContext())

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(activity, "Email and password are required", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
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
                    if (loginResponse?.status == "success") {
                        // Login berhasil, simpan status login
                        sharedPrefsHelper.setLoginStatus(true)

                        // Menampilkan Toast untuk sukses
                        Toast.makeText(activity, "Login successful!", Toast.LENGTH_SHORT).show()

                        // Pindahkan pengguna ke halaman utama
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(activity, "Login failed: ${loginResponse?.status}", Toast.LENGTH_SHORT).show()
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