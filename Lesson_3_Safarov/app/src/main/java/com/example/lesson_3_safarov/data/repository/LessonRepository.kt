package com.example.lesson_3_safarov.data.repository

import com.example.lesson6.data.requestmodel.RequestLogin
import com.example.lesson_3_safarov.data.ApiLesson
import com.example.lesson_3_safarov.data.responsemodel.ResponseLogin
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val apiLesson: ApiLesson,
) {

    suspend fun login(email: String, password: String): ResponseLogin {
        return apiLesson.login(RequestLogin(email, password)).data
    }
}