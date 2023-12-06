package com.example.lesson_3_safarov.data.responsemodel

import com.example.lesson_3_safarov.domain.profile.Profile
import com.google.gson.annotations.SerializedName

data class ResponseProfile(
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("occupation") val occupation: String,
    @SerializedName("avatarId") val avatarId: String,
)

fun ResponseProfile.toProfileDomain(): Profile {
    return Profile(
        name = this.name,
        surname = this.surname,
        occupation = this.occupation
    )
}