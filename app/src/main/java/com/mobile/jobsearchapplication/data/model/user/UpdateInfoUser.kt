package com.mobile.jobsearchapplication.data.model.user

import com.google.gson.annotations.SerializedName

data class UpdateInfoUser (
    @SerializedName("avatar") val avatar: String = "",

    @SerializedName("bio") val bio: String = "",

    @SerializedName("fullName") val fullName: String = "",

    @SerializedName("gender") val gender: String = "",

    @SerializedName("birthDay") val birthDay: String = "",

    @SerializedName("phoneNumber") val phoneNumber: String = "",

    @SerializedName("cvUrl") val cvUrl: String = "",
)