package com.example.cholestifyapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cholestifyapp.R
import com.example.cholestifyapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

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

        return binding!!.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}