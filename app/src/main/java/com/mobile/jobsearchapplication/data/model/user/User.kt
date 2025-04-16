package com.mobile.jobsearchapplication.data.model.user

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class User(
    @SerializedName("fullName") val fullName: String? = null,

    @SerializedName("phoneNumber") val phoneNumber: String? = null,

    @SerializedName("avatar") val avatar: String? = null,

    @SerializedName("bio") val bio: String? = null,

    @SerializedName("birthDay") val birthDay: String? = null,

    @SerializedName("gender") val gender: String? = null,

    @SerializedName("cvUrl") val cvUrl: String? = null,

    @SerializedName("favoritePosts") val favoritePosts: String? = null,
)