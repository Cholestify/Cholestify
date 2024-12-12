package com.example.cholestifyapp.ui.updateDaily

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cholestifyapp.data.response.DataItem
import com.example.cholestifyapp.databinding.FragmentUpdateDailyBinding


class UpdateDailyFragment : Fragment() {
    private var _binding: FragmentUpdateDailyBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UpdateAdapter
    private val updateViewModel by viewModels<UpdateViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateDailyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        adapter = UpdateAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Mengamati data makanan yang diterima
        updateViewModel.listEvent.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }

        // Mengamati status loading
        updateViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        // Tombol update
        binding.btnUpdateDailyFood.setOnClickListener {
//            val selectedItems = adapter.getSelectedItems()
//            if (selectedItems.isNotEmpty()) {
//                updateViewModel.sendSelectedFoodData(selectedItems)
//            } else {
//                Toast.makeText(requireContext(), "Pilih makanan yang ingin diperbarui", Toast.LENGTH_SHORT).show()
//            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
