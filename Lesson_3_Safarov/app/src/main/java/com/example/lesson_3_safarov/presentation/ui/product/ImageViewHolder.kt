package com.example.lesson_3_safarov.presentation.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.databinding.ImageListItemBinding

data class ImageItem(
    val imageUrl: String,
    val itemClickListener: (String) -> Unit,
    var isFocused: Boolean
)

class ImageViewHolder private constructor(private val binding: ImageListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): ImageViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ImageListItemBinding.inflate(layoutInflater, parent, false)
            return ImageViewHolder(binding)
        }
    }

    fun bind(
        item: ImageItem,
        setFocus: (ImageItem) -> Unit
    ) {
        binding.root.setOnClickListener {
            item.itemClickListener(item.imageUrl)
            setFocus(item)
        }
        if (item.isFocused) {
            binding.root.background =
                ContextCompat.getDrawable(binding.root.context, R.drawable.rounded_corner_stroke)
        } else binding.root.background =
            ContextCompat.getColor(binding.root.context, R.color.transparent).toDrawable()

        val cornerRadius = this.itemView.resources.getDimension(R.dimen.corner_radius_primary).toInt()
        Glide.with(binding.image)
            .load(item.imageUrl)
            .transform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCorners(cornerRadius)
                )
            )
            .placeholder(R.drawable.placeholder_insets)
            .into(binding.image)
    }
}