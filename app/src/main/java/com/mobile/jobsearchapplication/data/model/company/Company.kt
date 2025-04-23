package com.mobile.jobsearchapplication.data.model.company

import com.google.gson.annotations.SerializedName
import com.mobile.jobsearchapplication.data.model.job.Job
import java.util.*

data class Company(
    val id: String,
    val name: String,
    val description: String,
    val location: String,
    val website: String,
    val logo: String?,
    val size: Int,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("user_id") val userId: UUID
)
data class CompanyDetailResponse<T>(
    val data: Company?,
    val message: String
)