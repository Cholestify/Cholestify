package com.example.cholestifyapp.ui.updateDaily

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cholestifyapp.data.response.DataItem
import com.example.cholestifyapp.databinding.FragmentUpdateDailyBinding


class UpdateDaily : Fragment() {
    private var _binding: FragmentUpdateDailyBinding? = null
    private val binding get() = _binding!!

    private val UpdateViewModel by viewModels<UpdateViewModel>()
    private lateinit var adapter: UpdateAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateDailyBinding.inflate(inflater, container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UpdateAdapter()
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter

        UpdateViewModel.isLoading.observe(viewLifecycleOwner) {data ->
            showLoading(data)
        }

        UpdateViewModel.listEvent.observe(viewLifecycleOwner) {data ->
            adapter.submitList(data)
        }


    }


    private fun dataFood(food: List<DataItem>){
    }


    private fun showLoading(isLoading: Boolean){
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}