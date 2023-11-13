package com.example.lesson_3_safarov.domain.catalog.usecase

import com.example.lesson_3_safarov.data.repository.LessonRepository
import com.example.lesson_3_safarov.domain.catalog.Product
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val lessonRepository: LessonRepository,
) {
    suspend fun execute(): List<Product> {
        return lessonRepository.getProducts()
    }
}