package com.mobile.jobsearchapplication.data.model.job

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.*

data class Job(
    @SerializedName("id") val id: UUID,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("salaryMin") val salaryMin: BigDecimal?,
    @SerializedName("salaryMax") val salaryMax: BigDecimal?,
    @SerializedName("currency") val currency: String,
    @SerializedName("salaryPeriod") val salaryPeriod: String,
    @SerializedName("location") val location: String,
    @SerializedName("jobType") val jobType: String,
    @SerializedName("experienceLevel") val experienceLevel: String,
    @SerializedName("companyId") val companyId: String,
    @SerializedName("postedBy") val postedBy: String,
    @SerializedName("benefits") val benefits: String?,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("genderRequire") val genderRequire: String,
    @SerializedName("deadline") val deadline: String?,
    @SerializedName("status") val status: String,
    @SerializedName("requirements") val requirements: String?,
    @SerializedName("jobImage") val jobImage: String?,
    @SerializedName("createdAt") val createdAt: String
)

data class JobDetailResponse<T>(
    val data: Job?, // Chỉ là một Job, không phải PaginatedData
    val message: String
)