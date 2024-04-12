package com.example.lesson_3_safarov.data.responsemodel

import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import com.example.lesson_3_safarov.domain.catalog.Product as CatalogProduct
import com.example.lesson_3_safarov.domain.product.Product as ProductProduct

data class ResponseProduct(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("department") val department: String,
    @SerializedName("price") val price: Int,
    @SerializedName("badge") val badge: List<ResponseBadge>,
    @SerializedName("preview") val preview: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("sizes") val sizes: List<ResponseSize>,
    @SerializedName("description") val description: String,
    @SerializedName("details") val details: List<String>
)

fun ResponseProduct.toCatalogDomain(): CatalogProduct {
    return CatalogProduct(
        id = this.id,
        title = this.title,
        department = this.department,
        price = this.price.formatPrice(),
        preview = this.preview
    )
}

fun ResponseProduct.toProductDomain(): ProductProduct {
    return ProductProduct(
        id = id,
        title = title,
        department = department,
        price = price.formatPrice(),
        badge = badge.map { it.toDomain() },
        images = images,
        sizes = sizes.map { it.toDomain() },
        description = description,
        details = details,
    )
}

fun Int.formatPrice(): String {
    val decimalFormat = DecimalFormat("#,###")
    val symbols = DecimalFormatSymbols(Locale.getDefault())
    symbols.groupingSeparator = ' '
    decimalFormat.decimalFormatSymbols = symbols
    return decimalFormat.format(this) + " ₽"
}

