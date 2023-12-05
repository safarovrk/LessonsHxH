package com.example.lesson_3_safarov.data.repository

import com.example.lesson_3_safarov.data.requestmodel.RequestLogin
import com.example.lesson_3_safarov.data.ApiLesson
import com.example.lesson_3_safarov.data.requestmodel.RequestOrder
import com.example.lesson_3_safarov.data.responsemodel.ResponseLogin
import com.example.lesson_3_safarov.data.responsemodel.ResponseOrderCreation
import com.example.lesson_3_safarov.data.responsemodel.toCatalogDomain
import com.example.lesson_3_safarov.data.responsemodel.toProductDomain
import okhttp3.Response
import java.util.Date
import com.example.lesson_3_safarov.domain.catalog.Product as CatalogProduct
import com.example.lesson_3_safarov.domain.product.Product as ProductProduct
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val apiLesson: ApiLesson
) {

    suspend fun login(email: String, password: String): ResponseLogin {
        return apiLesson.login(RequestLogin(email, password)).data
    }

    suspend fun getProducts(): List<CatalogProduct> {
        return apiLesson.getProducts().data.map { it.toCatalogDomain() }
    }

    suspend fun getProduct(id: String): ProductProduct {
        return apiLesson.getProduct(id).data.toProductDomain()
    }

    suspend fun createOrder(
        house: String,
        apartment: String,
        dateDelivery: Date,
        id: String,
        size: String,
        quantity: Int
    ): ResponseOrderCreation {
        return apiLesson.createOrder(
            RequestOrder(
                house,
                apartment,
                dateDelivery,
                listOf(RequestOrder.ProductsRequest(id, size, quantity))
            )
        ).data
    }
}