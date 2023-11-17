package com.example.lesson_3_safarov.presentation.exception

import com.example.lesson_3_safarov.data.responsemodel.ErrorBaseResponse
import com.google.gson.Gson
import retrofit2.HttpException

fun Exception.getError(): String? {
    return if (this is HttpException) {
        val errorBody = response()?.errorBody()?.string()
        try {
            Gson().fromJson(errorBody, ErrorBaseResponse::class.java).error.message
        } catch (e: NullPointerException) {
            message
        }
    } else {
        message
    }
}