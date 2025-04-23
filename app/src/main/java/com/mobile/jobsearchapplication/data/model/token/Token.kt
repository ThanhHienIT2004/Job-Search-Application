package com.mobile.jobsearchapplication.data.model.token

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("id") val id: String? = null,
    @SerializedName("token") val token: String? = null
)