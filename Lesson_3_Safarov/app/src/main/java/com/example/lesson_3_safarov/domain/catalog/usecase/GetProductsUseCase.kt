package com.example.lesson_3_safarov.domain.catalog.usecase

import com.example.lesson_3_safarov.data.repository.DataBaseRepository
import com.example.lesson_3_safarov.data.repository.LessonRepository
import com.example.lesson_3_safarov.domain.catalog.Product
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val lessonRepository: LessonRepository,
    private val dataBaseRepository: DataBaseRepository
) {
    suspend fun execute(): List<Product> {
        return dataBaseRepository.getProducts().ifEmpty {
            val products = lessonRepository.getProducts()
            products.forEach { dataBaseRepository.addProduct(it) }
            return@ifEmpty products
        }
    }
}