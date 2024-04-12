package com.example.lesson_3_safarov.data.responsemodel

import com.example.lesson_3_safarov.domain.product.Badge
import com.google.gson.annotations.SerializedName

data class ResponseBadge(
    @SerializedName("value") val value: String,
    @SerializedName("color") val color: String
)

fun ResponseBadge.toDomain(): Badge {
    return Badge(
        value = value,
        color = color
    )
}
