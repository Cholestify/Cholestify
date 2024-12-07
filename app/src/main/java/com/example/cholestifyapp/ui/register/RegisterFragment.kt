package com.example.cholestifyapp.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cholestifyapp.R
import com.example.cholestifyapp.data.retrofit.ApiService
import com.example.cholestifyapp.data.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {

    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_register, container, false)

        fullNameEditText = rootView.findViewById(R.id.editTextText)
        emailEditText = rootView.findViewById(R.id.editTextTextEmailAddress)
        passwordEditText = rootView.findViewById(R.id.editTextTextPassword)
        signUpButton = rootView.findViewById(R.id.sign_up_btn)

        signUpButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(activity, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(fullName, email, password)
            }
        }

        return rootView
    }

    private fun registerUser(fullName: String, email: String, password: String) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val request = RegisterRequest(fullName, email, password)
        val call = apiService.register(request)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val registerResponse = response.body()
                    if (registerResponse?.status == "success") {
                        Toast.makeText(activity, "Registration successful!", Toast.LENGTH_SHORT).show()
                        // Lakukan tindakan setelah pendaftaran berhasil, misalnya, buka login fragment atau activity baru.
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