package com.example.lesson_3_safarov.domain.product.usecase

import com.example.lesson_3_safarov.data.repository.LessonRepository
import com.example.lesson_3_safarov.domain.product.Product
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val lessonRepository: LessonRepository,
) {
    suspend fun execute(id: String): Product {
        return lessonRepository.getProduct(id)
    }
}