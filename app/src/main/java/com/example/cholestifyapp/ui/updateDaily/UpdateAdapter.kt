package com.example.cholestifyapp.ui.updateDaily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cholestifyapp.data.response.DataItem
import com.example.cholestifyapp.databinding.ItemAddFoodBinding

class UpdateAdapter(
    private val onItemClick: (DataItem) -> Unit = {}
) : ListAdapter<DataItem, UpdateAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private val selectedItems = mutableSetOf<DataItem>()

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MyViewHolder(private val binding: ItemAddFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataItem, isSelected: Boolean, onItemClick: (DataItem) -> Unit) {
            binding.tvItemName.text = item.food ?: "Unknown Food"
            binding.checkboxItem.isChecked = isSelected

            binding.checkboxItem.setOnCheckedChangeListener(null)
            binding.checkboxItem.isChecked = isSelected
//
//            binding.checkboxItem.setOnCheckedChangeListener { _, isChecked ->
//                onItemClick(item.apply { isSelected = isChecked })
//            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemAddFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, selectedItems.contains(item)) { selectedItem ->
            if (selectedItems.contains(selectedItem)) {
                selectedItems.remove(selectedItem)
            } else {
                selectedItems.add(selectedItem)
            }
            notifyItemChanged(position)
        }
    }

    fun getSelectedItems(): List<DataItem> = selectedItems.toList()
}
