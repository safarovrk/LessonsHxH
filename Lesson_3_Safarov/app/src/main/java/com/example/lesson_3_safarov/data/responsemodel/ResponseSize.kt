package com.example.lesson_3_safarov.data.responsemodel

import com.google.gson.annotations.SerializedName

data class ResponseSize(
    @SerializedName("value") val value: String,
    @SerializedName("isAvailable") val isAvailable: Boolean
)
