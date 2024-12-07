package com.example.cholestifyapp.ui.register

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_register, container, false)

        // Inisialisasi view
        nameEditText = rootView.findViewById(R.id.editTextTextRegist)
        emailEditText = rootView.findViewById(R.id.EmailAddressRegist)
        passwordEditText = rootView.findViewById(R.id.TextPasswordRegist)
        registerButton = rootView.findViewById(R.id.sign_up_btn)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(activity, "Name, Email, and Password are required", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(name, email, password)
            }
        }

        return rootView
    }

    private fun registerUser(name: String, email: String, password: String) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val request = RegisterRequest(name, email, password)
        val call = apiService.register(request)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val registerResponse = response.body()
                    if (registerResponse != null && !registerResponse.error) {
                        // Menampilkan Toast untuk sukses
                        Toast.makeText(activity, registerResponse.message, Toast.LENGTH_SHORT).show()

                        // Pindahkan pengguna ke login atau halaman utama
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Toast.makeText(activity, "Registration failed: ${registerResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(activity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(activity, "Failed to connect to the server", Toast.LENGTH_SHORT).show()
            }
        })
    }
}