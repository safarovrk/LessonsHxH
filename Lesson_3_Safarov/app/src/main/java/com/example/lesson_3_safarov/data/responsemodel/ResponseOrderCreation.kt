package com.example.lesson_3_safarov.data.responsemodel

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ResponseOrderCreation(
    @SerializedName("number") val number: Int,
    @SerializedName("createdDelivery") val createdDelivery: Date,
)
