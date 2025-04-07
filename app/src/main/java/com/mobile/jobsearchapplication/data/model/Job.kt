package com.mobile.jobsearchapplication.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.*

data class Job(
    val id: UUID,
    val title: String,
    val description: String,
    @SerializedName("salaryMin") val salaryMin: BigDecimal?,
    @SerializedName("salaryMax") val salaryMax: BigDecimal?,
    val currency: String,
    val location: String,
    @SerializedName("jobType") val jobType: String,
    @SerializedName("experienceLevel") val experienceLevel: String,
    @SerializedName("companyId") val companyId: UUID,
    @SerializedName("postedBy") val postedBy: UUID,
    val benefits: String?,
    val quantity: Int,
    @SerializedName("genderRequire") val genderRequire: String,
    val deadline: String?, // Thay vì Date vì API trả về String
    val status: String,
    val requirements: String?,
    @SerializedName("jobImage") val jobImage: String?,
    @SerializedName("createdAt") val createdAt: String // Thay vì Date vì API trả về String
)

data class JobDetailResponse<T>(
    val data: Job, // Chỉ là một Job, không phải PaginatedData
    val message: String
)