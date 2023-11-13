package com.example.lesson_3_safarov.presentation.ui.catalog

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.databinding.CatalogListItemBinding
import com.example.lesson_3_safarov.domain.catalog.Product

data class ProductItem(
    val product: Product,
    val clickListener: (Product) -> Unit
)

class CatalogViewHolder private constructor(private val binding: CatalogListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): CatalogViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CatalogListItemBinding.inflate(layoutInflater, parent, false)
            return CatalogViewHolder(binding)
        }
    }

    fun bind(item: ProductItem, clickListener: (Product) -> Unit) {
        binding.productTitle.text = item.product.title
        binding.productCountry.text = item.product.department
        binding.productPrice.text = item.product.price
        binding.buyButton.setOnClickListener { clickListener(item.product) }

        Glide.with(binding.productImage)
            .load(item.product.preview)
            .transform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCorners(8 * Resources.getSystem().displayMetrics.density.toInt())
                )
            )
            .placeholder(R.drawable.dallas_cowboys)
            .into(binding.productImage)
    }
}