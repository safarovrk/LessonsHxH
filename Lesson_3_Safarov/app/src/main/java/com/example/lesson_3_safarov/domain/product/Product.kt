package com.example.lesson_3_safarov.domain.product

data class Product(
    val id: String,
    val title: String,
    val department: String,
    val price: String,
    val badge: List<Badge>,
    val images: List<String>,
    val sizes: List<Size>,
    val description: String,
    val details: List<String>
)