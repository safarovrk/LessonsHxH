package com.example.lesson_3_safarov.data.repository

import com.example.lesson_3_safarov.data.requestmodel.RequestLogin
import com.example.lesson_3_safarov.data.ApiLesson
import com.example.lesson_3_safarov.data.responsemodel.ResponseLogin
import com.example.lesson_3_safarov.data.responsemodel.toDomain
import com.example.lesson_3_safarov.data.storage.dao.ProductDao
import com.example.lesson_3_safarov.data.storage.entities.ProductEntity
import com.example.lesson_3_safarov.domain.catalog.Product
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val apiLesson: ApiLesson,
    private val productDao: ProductDao
) {

    suspend fun login(email: String, password: String): ResponseLogin {
        return apiLesson.login(RequestLogin(email, password)).data
    }

    suspend fun getProducts(): List<Product> {
        return productDao.getProducts().map { it.toProduct() }.ifEmpty {
            val products = apiLesson.getProducts().data.map { it.toDomain() }
            products.forEach { productDao.addProduct(ProductEntity.fromProduct(it)) }
            return@ifEmpty products
        }
    }
}