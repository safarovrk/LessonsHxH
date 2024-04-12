package com.example.lesson_3_safarov.presentation.ui.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class CatalogAdapter @Inject constructor() : RecyclerView.Adapter<CatalogViewHolder>() {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductItem>() {
            override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean =
                oldItem.product.id == newItem.product.id

            override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean =
                oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(products: List<ProductItem>) {
        differ.submitList(products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder.from(parent)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}