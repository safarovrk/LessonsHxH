package com.example.lesson_3_safarov.data.requestmodel

import com.google.gson.annotations.SerializedName

data class RequestLogin(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)