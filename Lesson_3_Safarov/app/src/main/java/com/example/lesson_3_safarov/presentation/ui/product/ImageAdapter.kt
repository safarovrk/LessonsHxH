package com.example.lesson_3_safarov.presentation.ui.product

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class ImageAdapter @Inject constructor() : RecyclerView.Adapter<ImageViewHolder>() {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageItem>() {
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean =
                oldItem.imageUrl == newItem.imageUrl

            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean =
                oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(images: List<ImageItem>) {
        differ.submitList(images)
    }

    private fun setFocus(imageItem: ImageItem) {
        // Создание идентичной коллекции по другой ссылке
        val changedFocusList = differ.currentList.map { it.copy() }
        changedFocusList.forEach {
            it.isFocused = it == imageItem
        }
        submitList(changedFocusList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.from(parent)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(differ.currentList[position]) { setFocus(differ.currentList[position]) }
    }
}