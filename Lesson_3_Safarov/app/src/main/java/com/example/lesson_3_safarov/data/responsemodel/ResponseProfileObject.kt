package com.example.lesson_3_safarov.data.responsemodel

import com.google.gson.annotations.SerializedName

data class ResponseProfileObject(
    @SerializedName("profile") val profile: ResponseProfile
)
