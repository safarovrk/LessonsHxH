package com.example.lesson_3_safarov.data.responsemodel

import com.example.lesson_3_safarov.domain.catalog.Product
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

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

fun ResponseProduct.toDomain(): Product {
    val decimalFormat = DecimalFormat("#,###")
    val symbols = DecimalFormatSymbols(Locale.getDefault())
    symbols.groupingSeparator = ' '
    decimalFormat.decimalFormatSymbols = symbols

    return Product(
        id = this.id,
        title = this.title,
        department = this.department,
        price = decimalFormat.format(this.price) + " ₽",
        preview = this.preview
    )
}

