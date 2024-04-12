package com.example.lesson_3_safarov.presentation.ui.catalog

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
    val buttonClickListener: (Product) -> Unit,
    val itemClickListener: (Product) -> Unit
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

    fun bind(item: ProductItem) {
        binding.productTitle.text = item.product.title
        binding.productCountry.text = item.product.department
        binding.productPrice.text = item.product.price
        binding.buyButton.setOnClickListener { item.buttonClickListener(item.product) }

        val cornerRadius = this.itemView.resources.getDimension(R.dimen.corner_radius_primary).toInt()
        Glide.with(binding.productImage)
            .load(item.product.preview)
            .transform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCorners(cornerRadius)
                )
            )
            .placeholder(R.drawable.placeholder_insets)
            .into(binding.productImage)

        binding.root.setOnClickListener {
            item.itemClickListener(item.product)
        }
    }
}