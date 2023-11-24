package com.example.lesson_3_safarov.domain.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Size(
    val value: String,
    val isAvailable: Boolean
) : Parcelable
