package com.example.cholestifyapp.ui.updateDaily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cholestifyapp.data.response.DataItem
import com.example.cholestifyapp.databinding.ItemAddFoodBinding

class UpdateAdapter(private val onItemClick: (DataItem) -> Unit) : ListAdapter<DataItem, UpdateAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                // Memeriksa apakah dua item adalah objek yang sama berdasarkan ID atau atribut unik lainnya.
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                // Memeriksa apakah konten dua item sama
                return oldItem == newItem
            }
        }
    }

    class MyViewHolder(private val binding: ItemAddFoodBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Mengikat data `ListEventsItem` ke tampilan dalam layout item.
         *
         * @param item Objek `ListEventsItem` yang berisi data event.
         * @param onItemClick Fungsi yang dipanggil ketika item diklik.
         */
        fun bind(item: DataItem, onItemClick: (DataItem) -> Unit) {
            // Menampilkan data event pada tampilan
            binding.tvItemName


            // Menambahkan tindakan klik pada root layout item untuk membuka detail event
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    //    Metode onBindViewHolder dan onCreateViewHolder adalah contoh polymorphism, di mana metode yang sama didefinisikan dalam kelas induk (ListAdapter) dan diimplementasikan di kelas turunan (UpcomingAdapter). Ini memungkinkan UpcomingAdapter untuk memberikan perilaku spesifiknya sendiri.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Menginflate layout item `ItemEventListBinding` dan menginisialisasi ViewHolder
        val binding = ItemAddFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Mengikat data event ke ViewHolder pada posisi tertentu
        val event = getItem(position)
        holder.bind(event, onItemClick)
    }
}