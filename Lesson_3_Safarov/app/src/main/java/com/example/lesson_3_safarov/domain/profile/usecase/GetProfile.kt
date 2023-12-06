package com.example.lesson_3_safarov.domain.profile.usecase

import com.example.lesson_3_safarov.data.repository.LessonRepository
import com.example.lesson_3_safarov.domain.profile.Profile
import javax.inject.Inject

class GetProfile @Inject constructor(
    private val lessonRepository: LessonRepository
) {
    suspend fun execute(): Profile {
        return lessonRepository.getProfile()
    }
}