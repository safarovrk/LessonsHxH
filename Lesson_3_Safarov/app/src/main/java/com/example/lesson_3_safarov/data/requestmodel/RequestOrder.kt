package com.example.lesson_3_safarov.data.requestmodel

import com.google.gson.annotations.SerializedName
import java.util.Date

data class RequestOrder(
    @SerializedName("House") val house: String,
    @SerializedName("Apartment") val apartment: String,
    @SerializedName("DateDelivery") val dateDelivery: Date,
    @SerializedName("Products") val products: List<ProductsRequest>,
) {
    data class ProductsRequest(
        @SerializedName("ProductId") val productId: String,
        @SerializedName("Size") val size: String,
        @SerializedName("Quantity") val quantity: Int,
    )
}
