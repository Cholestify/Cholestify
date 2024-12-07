package com.example.cholestifyapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cholestifyapp.R
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

        // Menampilkan data pengguna (contoh)
        binding!!.ivProfilePicture.setImageResource(R.drawable.ic_person)
        binding!!.tvProfileName.text = "lOREM IPSUM 53"
        binding!!.tvProfileEmail.text = "LOREM IPSUM 53"
        binding!!.tvPersonalInformation.text = "Personal Information"
        binding!!.edFirstName.hint = ""
        binding!!.edLastName.hint = ""
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

        // Dapatkan NavController dari graf yang sesuai
        val navController = findNavController()

        // Ganti graf ke auth_nav_graph untuk menuju ke LoginFragment
        navController.setGraph(R.navigation.auth_nav_graph)

        // Navigasi ke LoginFragment
        navController.navigate(R.id.loginFragment) // Menavigasi langsung ke LoginFragment

        // Menutup aktivitas atau fragment
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}