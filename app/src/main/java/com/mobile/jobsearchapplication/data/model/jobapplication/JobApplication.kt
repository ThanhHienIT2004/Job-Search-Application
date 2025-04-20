package com.mobile.jobsearchapplication.data.model.jobapplication

import com.google.gson.annotations.SerializedName

data class JobApplication(
    @SerializedName("userId") val userId: String,
    @SerializedName("jobId") val jobId: String,
    @SerializedName("status") val status: String,
    @SerializedName("coverLetter") val coverLetter: String,
    @SerializedName("cvUrl") val cvUrl: String,
    @SerializedName("additionalInfo") val additionalInfo: String
)