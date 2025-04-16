package com.mobile.jobsearchapplication.data.model.job

import com.google.gson.annotations.SerializedName

data class JobByCategory(
    @SerializedName("data") val data: List<Job>,
    @SerializedName("message") val message: String
)