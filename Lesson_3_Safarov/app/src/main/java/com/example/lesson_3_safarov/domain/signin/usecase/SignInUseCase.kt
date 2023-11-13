package com.example.lesson_3_safarov.domain.signin.usecase

import com.example.lesson_3_safarov.data.repository.LessonRepository
import com.example.lesson_3_safarov.data.repository.PreferenceStorage
import com.example.lesson_3_safarov.data.responsemodel.ResponseLogin
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val lessonRepository: LessonRepository,
    private val preferenceStorage: PreferenceStorage
) {
    suspend fun execute(email: String, password: String): ResponseLogin {
        val loginData = lessonRepository.login(email, password)
        preferenceStorage.setValue(PreferenceStorage.PREF_TOKEN_KEY, loginData.accessToken)
        return loginData
    }
}