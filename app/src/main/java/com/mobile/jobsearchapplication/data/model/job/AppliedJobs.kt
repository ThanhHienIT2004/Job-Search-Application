package com.mobile.jobsearchapplication.data.model.job

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.UUID

data class AppliedJobs(
    @SerializedName("id") val id: UUID,
    @SerializedName("title") val title: String,
    @SerializedName("salaryMin") val salaryMin: BigDecimal?,
    @SerializedName("salaryMax") val salaryMax: BigDecimal?,
    @SerializedName("currency") val currency: String = "VND",
    @SerializedName("location") val location: String,
    @SerializedName("jobImage") val jobImage: String?,
    @SerializedName("statusResponse") val statusResponse: String,
    @SerializedName("createdAt") val createdAt: String
)
