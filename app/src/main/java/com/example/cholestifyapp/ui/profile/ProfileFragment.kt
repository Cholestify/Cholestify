package com.example.cholestifyapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cholestifyapp.R
import com.example.cholestifyapp.databinding.FragmentProfileBinding
import com.example.cholestifyapp.ui.login.LoginFragment
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

        // Arahkan ke halaman login setelah logout
        val intent = Intent(requireContext(), LoginFragment::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Menghapus aktivitas sebelumnya
        startActivity(intent)

        // Tutup aktivitas saat ini
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}