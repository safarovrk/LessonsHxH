package com.example.lesson_3_safarov.presentation.ui.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PreOrderProduct(
    val id: String,
    val title: String,
    val department: String,
    val price: String,
    val preview: String,
    val size: String
) : Parcelable
