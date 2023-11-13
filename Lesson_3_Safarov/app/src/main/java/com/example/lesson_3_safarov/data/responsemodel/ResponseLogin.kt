package com.example.lesson_3_safarov.data.responsemodel

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("profile") val profile: ResponseProfile,
)