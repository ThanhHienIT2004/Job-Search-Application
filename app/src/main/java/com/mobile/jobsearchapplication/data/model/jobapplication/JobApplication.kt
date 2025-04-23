package com.mobile.jobsearchapplication.data.model.jobapplication

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.UUID

data class JobApplication(
    @SerializedName("userId") val userId: String,
    @SerializedName("jobId") val jobId: String,
    @SerializedName("status") val status: String,
    @SerializedName("coverLetter") val coverLetter: String,
    @SerializedName("cvUrl") val cvUrl: String,
    @SerializedName("additionalInfo") val additionalInfo: String
)

data class AppliedUserWithApplication(
    val userId: String,
    val fullName: String?,
    val phoneNumber: String?,
    val avatar: String?,
    val bio: String?,
    val birthDay: String?,
    val gender: String?,
    val userCvUrl: String?,
    val favoritePosts: String?,
    val userCreatedAt: String,
    val userUpdatedAt: String,
    val role: String,
    val jobId: UUID,
    val applicationStatus: String,
    val applicationCreatedAt: String,
    val coverLetter: String,
    val additionalInfo: String,
    val applicationCvUrl: String
)

data class UpdateAppliedStatus(
    val userId: String,
    val jobId: UUID,
    val status: String
)