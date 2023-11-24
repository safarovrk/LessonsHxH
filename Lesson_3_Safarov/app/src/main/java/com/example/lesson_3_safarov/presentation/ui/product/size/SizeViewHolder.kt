package com.example.lesson_3_safarov.presentation.ui.product.size

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.databinding.SizeListItemBinding
import com.example.lesson_3_safarov.domain.product.Size

data class SizeItem(
    val size: Size,
    val itemClickListener: (Size) -> Unit
)

class SizeViewHolder private constructor(private val binding: SizeListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): SizeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SizeListItemBinding.inflate(layoutInflater, parent, false)
            return SizeViewHolder(binding)
        }
    }

    fun bind(
        item: SizeItem
    ) {
        if (item.size.isAvailable) {
            binding.root.text = item.size.value
            binding.root.setOnClickListener {
                item.itemClickListener(item.size)
            }
        } else {
            binding.root.hint = item.size.value
            binding.root.background =
                ContextCompat.getDrawable(binding.root.context, R.color.seashell)
        }
    }
}