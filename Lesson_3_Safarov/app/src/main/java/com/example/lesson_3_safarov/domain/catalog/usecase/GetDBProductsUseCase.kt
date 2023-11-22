package com.example.lesson_3_safarov.domain.catalog.usecase

import com.example.lesson_3_safarov.data.repository.DataBaseRepository
import com.example.lesson_3_safarov.domain.catalog.Product
import javax.inject.Inject

class GetDBProductsUseCase @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) {
    suspend fun execute(): List<Product> {
        return dataBaseRepository.getProducts()
    }
}