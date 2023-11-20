package com.example.lesson_3_safarov.data.repository

import com.example.lesson_3_safarov.data.requestmodel.RequestLogin
import com.example.lesson_3_safarov.data.ApiLesson
import com.example.lesson_3_safarov.data.responsemodel.ResponseLogin
import com.example.lesson_3_safarov.data.responsemodel.toCatalogDomain
import com.example.lesson_3_safarov.data.responsemodel.toProductDomain
import com.example.lesson_3_safarov.data.storage.dao.ProductDao
import com.example.lesson_3_safarov.data.storage.entities.ProductEntity
import com.example.lesson_3_safarov.domain.catalog.Product as CatalogProduct
import com.example.lesson_3_safarov.domain.product.Product as ProductProduct
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val apiLesson: ApiLesson,
    private val productDao: ProductDao
) {

    suspend fun login(email: String, password: String): ResponseLogin {
        return apiLesson.login(RequestLogin(email, password)).data
    }

    suspend fun getProducts(): List<CatalogProduct> {
        return productDao.getProducts().map { it.toProduct() }.ifEmpty {
            val products = apiLesson.getProducts().data.map { it.toCatalogDomain() }
            products.forEach { productDao.addProduct(ProductEntity.fromProduct(it)) }
            return@ifEmpty products
        }
    }

    suspend fun getProduct(id: String): ProductProduct {
        return apiLesson.getProduct(id).data.toProductDomain()
    }
}