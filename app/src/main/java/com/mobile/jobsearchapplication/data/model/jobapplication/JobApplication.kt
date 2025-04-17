package com.mobile.jobsearchapplication.data.model.jobapplication

import com.google.gson.annotations.SerializedName

data class JobApplication(
    @SerializedName("jobId") val jobId: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("status") val status: String,
    @SerializedName("coverLetter") val coverLetter: String,
    @SerializedName("cvUrl") val cvUrl: String,
    @SerializedName("additionalInfo") val additionalInfo: String
)