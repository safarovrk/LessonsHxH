package com.example.lesson_3_safarov.presentation.ui.product.size

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class SizeAdapter @Inject constructor() : RecyclerView.Adapter<SizeViewHolder>() {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SizeItem>() {
            override fun areItemsTheSame(oldItem: SizeItem, newItem: SizeItem): Boolean =
                oldItem.size.value == newItem.size.value

            override fun areContentsTheSame(oldItem: SizeItem, newItem: SizeItem): Boolean =
                oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(sizes: List<SizeItem>) {
        differ.submitList(sizes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        return SizeViewHolder.from(parent)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}