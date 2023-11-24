package com.example.lesson_3_safarov.data.repository

import com.example.lesson_3_safarov.data.storage.dao.ProductDao
import com.example.lesson_3_safarov.data.storage.entities.ProductEntity
import com.example.lesson_3_safarov.domain.catalog.Product
import javax.inject.Inject

class DataBaseRepository @Inject constructor(
    private val productDao: ProductDao
) {
    suspend fun getProducts(): List<Product> {
        return productDao.getProducts().map { it.toProduct() }
    }

    suspend fun addProduct(product: Product) {
        productDao.addProduct(ProductEntity.fromProduct(product))
    }
}