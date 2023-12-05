package com.example.lesson_3_safarov.domain.order.usecase

import com.example.lesson_3_safarov.data.repository.LessonRepository
import com.example.lesson_3_safarov.data.responsemodel.ResponseOrderCreation
import java.util.Date
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val lessonRepository: LessonRepository,
) {
    suspend fun execute(
        house: String,
        apartment: String,
        dateDelivery: Date,
        id: String,
        size: String,
        quantity: Int
    ): ResponseOrderCreation {
        return lessonRepository.createOrder(house, apartment, dateDelivery, id, size, quantity)
    }
}